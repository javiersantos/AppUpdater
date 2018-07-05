package com.github.javiersantos.appupdater;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;
import com.github.javiersantos.appupdater.objects.Update;
import com.github.javiersantos.appupdater.objects.Version;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class UtilsLibrary {

    static String getAppName(Context context) {
        return context.getString(context.getApplicationInfo().labelRes);
    }

    static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    static String getAppInstalledVersion(Context context) {
        String version = "0.0.0.0";

        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    static Integer getAppInstalledVersionCode(Context context) {
        Integer versionCode = 0;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    static Boolean isUpdateAvailable(Update installedVersion, Update latestVersion) {
        if (latestVersion.getLatestVersionCode() != null && latestVersion.getLatestVersionCode() > 0) {
            return latestVersion.getLatestVersionCode() > installedVersion.getLatestVersionCode();
        } else {
            if (!TextUtils.equals(installedVersion.getLatestVersion(), "0.0.0.0") && !TextUtils.equals(latestVersion.getLatestVersion(), "0.0.0.0")) {
                try
                {
                    final Version installed = new Version(installedVersion.getLatestVersion());
                    final Version latest = new Version(latestVersion.getLatestVersion());
                    return installed.compareTo(latest) < 0;
                } catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            } else return false;
        }
    }

    static Boolean isStringAVersion(String version) {
        return version.matches(".*\\d+.*");
    }

    static Boolean isStringAnUrl(String s) {
        Boolean res = false;
        try {
            new URL(s);
            res = true;
        } catch (MalformedURLException ignored) {}

        return res;
    }

    static Boolean getDurationEnumToBoolean(Duration duration) {
        Boolean res = false;

        switch (duration) {
            case INDEFINITE:
                res = true;
                break;
        }

        return res;
    }

    private static URL getUpdateURL(Context context, UpdateFrom updateFrom, GitHub gitHub) {
        String res;

        switch (updateFrom) {
            default:
                res = String.format(Config.PLAY_STORE_URL, getAppPackageName(context), Locale.getDefault().getLanguage());
                break;
            case GITHUB:
                res = Config.GITHUB_URL + gitHub.getGitHubUser() + "/" + gitHub.getGitHubRepo() + "/releases/latest";
                break;
            case AMAZON:
                res = Config.AMAZON_URL + getAppPackageName(context);
                break;
            case FDROID:
                res = Config.FDROID_URL + getAppPackageName(context);
                break;
        }

        try {
            return new URL(res);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    static Update getLatestAppVersionStore(Context context, UpdateFrom updateFrom, GitHub gitHub) {
        switch (updateFrom) {
            case GOOGLE_PLAY:
                return getLatestAppVersionGooglePlay(context);
            default:
                return getLatestAppVersionHttp(context, updateFrom, gitHub);
        }
    }

    private static Update getLatestAppVersionGooglePlay(Context context) {
        String version = "0.0.0.0";
        String recentChanges = "";

        URL updateURL = getUpdateURL(context, UpdateFrom.GOOGLE_PLAY, null);

        try {
            version = getJsoupString(updateURL.toString(), ".hAyfc .htlgb", 7);

            //TODO: Release Notes for Google Play is not working
            //recentChanges = getJsoupString(updateURL.toString(), ".W4P4ne .DWPxHb", 1);

            if (TextUtils.isEmpty(version)) {
                Log.e("AppUpdater", "Cannot retrieve latest version. Is it configured properly?");
            }
        } catch (Exception e) {
            Log.e("AppUpdater", "App wasn't found in the provided source. Is it published?");
        }

        Log.e("Update", version);

        return new Update(version, recentChanges, updateURL);
    }

    private static String getJsoupString(String url, String css, int position) throws Exception {
        return Jsoup.connect(url)
                .timeout(30000)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .get()
                .select(css)
                .get(position)
                .ownText();
    }

    private static Update getLatestAppVersionHttp(Context context, UpdateFrom updateFrom, GitHub gitHub) {
        Boolean isAvailable = false;
        String source = "";
        OkHttpClient client = new OkHttpClient();
        URL url = getUpdateURL(context, updateFrom, gitHub);
        Request request = new Request.Builder()
                .url(url)
                .build();
        ResponseBody body = null;

        try {
            Response response = client.newCall(request).execute();
            body = response.body();
            BufferedReader reader = new BufferedReader(new InputStreamReader(body.byteStream(), "UTF-8"));
            StringBuilder str = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                switch (updateFrom) {
                    case GITHUB:
                        if (line.contains(Config.GITHUB_TAG_RELEASE)) {
                            str.append(line);
                            isAvailable = true;
                        }
                        break;
                    case AMAZON:
                        if (line.contains(Config.AMAZON_TAG_RELEASE)) {
                            str.append(line);
                            isAvailable = true;
                        }
                        break;
                    case FDROID:
                        if (line.contains(Config.FDROID_TAG_RELEASE)) {
                            str.append(line);
                            isAvailable = true;
                        }
                }
            }

            if (str.length() == 0) {
                Log.e("AppUpdater", "Cannot retrieve latest version. Is it configured properly?");
            }

            response.body().close();
            source = str.toString();
        } catch (FileNotFoundException e) {
            Log.e("AppUpdater", "App wasn't found in the provided source. Is it published?");
        } catch (IOException ignore) {

        } finally {
            if (body != null) {
                body.close();
            }
        }

        final String version = getVersion(updateFrom, isAvailable, source);
        final URL updateUrl = getUpdateURL(context, updateFrom, gitHub);

        return new Update(version, updateUrl);
    }

    private static String getVersion(UpdateFrom updateFrom, Boolean isAvailable, String source) {
        String version = "0.0.0.0";
        if (isAvailable) {
            switch (updateFrom) {
                case GITHUB:
                    String[] splitGitHub = source.split(Config.GITHUB_TAG_RELEASE);
                    if (splitGitHub.length > 1) {
                        splitGitHub = splitGitHub[1].split("(\")");
                        version = splitGitHub[0].trim();
                        if (version.startsWith("v"))
                        { // Some repo uses vX.X.X
                            splitGitHub = version.split("(v)", 2);
                            version = splitGitHub[1].trim();
                        }
                    }
                    break;
                case AMAZON:
                    String[] splitAmazon = source.split(Config.AMAZON_TAG_RELEASE);
                    splitAmazon = splitAmazon[1].split("(<)");
                    version = splitAmazon[0].trim();
                    break;
                case FDROID:
                    String[] splitFDroid = source.split(Config.FDROID_TAG_RELEASE);
                    splitFDroid = splitFDroid[1].split("(<)");
                    version = splitFDroid[0].trim();
                    break;
            }
        }
        return version;
    }

    static Update getLatestAppVersion(UpdateFrom updateFrom, String url) {
        if (updateFrom == UpdateFrom.XML){
            ParserXML parser = new ParserXML(url);
            return parser.parse();
        } else {
            return new ParserJSON(url).parse();
        }
    }


    static Intent intentToUpdate(Context context, UpdateFrom updateFrom, URL url) {
        Intent intent;

        if (updateFrom.equals(UpdateFrom.GOOGLE_PLAY)) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getAppPackageName(context)));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        }

        return intent;
    }

    static void goToUpdate(Context context, UpdateFrom updateFrom, URL url) {
        Intent intent = intentToUpdate(context, updateFrom, url);

        if (updateFrom.equals(UpdateFrom.GOOGLE_PLAY)) {
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
                context.startActivity(intent);
            }
        } else {
            context.startActivity(intent);
        }
    }

    static Boolean isAbleToShow(Integer successfulChecks, Integer showEvery) {
        return successfulChecks % showEvery == 0;
    }

    static Boolean isNetworkAvailable(Context context) {
        Boolean res = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                res = networkInfo.isConnected();
            }
        }

        return res;
    }

}
