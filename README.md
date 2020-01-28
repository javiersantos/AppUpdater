<h1 align="center">AppUpdater <a href="https://github.com/javiersantos/AppUpdater#how-to-include"><img src="https://jitpack.io/v/javiersantos/AppUpdater.svg"></a></h1>
<h4 align="center">Android Library</h4>

<p align="center">
  <a target="_blank" href="https://android-arsenal.com/api?level=8"><img src="https://img.shields.io/badge/API-9%2B-orange.svg"></a>
  <a target="_blank" href="https://travis-ci.org/javiersantos/AppUpdater"><img src="https://travis-ci.org/javiersantos/AppUpdater.svg?branch=master"></a>
  <a target="_blank" href="http://android-arsenal.com/details/1/3094"><img src="https://img.shields.io/badge/Android%20Arsenal-AppUpdater-blue.svg"></a>
</p>

<p align="center">Android Library that checks for updates on Google Play, GitHub, Amazon, F-Droid or your own server. This library notifies your apps' updates by showing a Material dialog, Snackbar or notification. Check out the <a href="https://github.com/javiersantos/AppUpdater/wiki">wiki</a>.</p>

## Sample Project
You can download the latest sample APK from Google Play:

<a target="_blank" href="https://play.google.com/store/apps/details?id=com.github.javiersantos.appupdater.demo"><img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="60"></a>

## How to include
Add the repository to your project **build.gradle**:
```Gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

And add the library to your module **build.gradle**:
```Gradle
dependencies {
    implementation 'com.github.javiersantos:AppUpdater:2.7'
}
```

## Usage

### Activity / Fragment
By default, the basic usage will show a default dialog when a new version is found on the Play Store (otherwise nothing will be shown). By calling the `.start()` method, the library will work in background. You can cancel it at any time by calling `.stop()`. Other customizations are explained below.

#### Activity
```Java
AppUpdater appUpdater = new AppUpdater(this);
appUpdater.start();
```

#### Fragment
```Java
AppUpdater appUpdater = new AppUpdater(getActivity());
appUpdater.start();
```

## Customizations ([Wiki](https://github.com/javiersantos/AppUpdater/wiki))

### Displaying a dialog, Snackbar or notification
The default usage is configured to display a dialog. However, there are other ways to show the update notice.

```Java
new AppUpdater(this)
	.setDisplay(Display.SNACKBAR)
	.setDisplay(Display.DIALOG)
	.setDisplay(Display.NOTIFICATION)
	...
```

When using `Display.DIALOG`, you can make the dialog dismissable when touching outside by using `.setCancelable(false)` (enabled by default).

When using `Display.SNACKBAR`, you can change the duration by using `.setDuration(Duration.NORMAL)` (default) or 
`.setDuration(Duration.INDEFINITE)`.

### Providing a source for the updates
By default the library will check for updates on the Play Store. However, there are other alternatives, such as GitHub, Amazon, F-Droid or using your own server.

```Java
new AppUpdater(this)
	.setUpdateFrom(UpdateFrom.GITHUB)
	.setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
	.setUpdateFrom(UpdateFrom.AMAZON)
	.setUpdateFrom(UpdateFrom.FDROID)
	.setUpdateFrom(UpdateFrom.XML)
	.setUpdateFrom(UpdateFrom.JSON)
	...
```

When using GitHub you must provide the repo where the library will check for updates: `.setGitHubUserAndRepo("javiersantos", "AppUpdater")`. Check out the [wiki](https://github.com/javiersantos/AppUpdater/wiki/UpdateFrom.GITHUB) for more details.

When using the XML source you must upload a .xml file somewhere on the Internet following the structure explained in the [wiki](https://github.com/javiersantos/AppUpdater/wiki/UpdateFrom.XML) and add the URL as shown in this example: `.setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.xml")`.

When using the JSON source you must upload a .json file somewhere on the Internet following the structure explained in the [wiki](https://github.com/javiersantos/AppUpdater/wiki/UpdateFrom.JSON) and add the URL as shown in this example: `.setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.json")`.

A detailed description with examples is available at: https://github.com/javiersantos/AppUpdater/wiki

### Setting the frequency to show updates
By default, a dialog/Snackbar/notification will be shown whenever a new version is found. However, this can be set to show only every X times that the app ascertains that a new update is available.

```Java
new AppUpdater(this)
	.showEvery(5)
	...
```

You can also show the dialog, Snackbar or notification although there aren't updates by using `.showAppUpdated(true)` (disabled by default).

### Customizing the title, description, buttons and more

```Java
new AppUpdater(this)
	.setTitleOnUpdateAvailable("Update available")
	.setContentOnUpdateAvailable("Check out the latest version available of my app!")
	.setTitleOnUpdateNotAvailable("Update not available")
	.setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
	.setButtonUpdate("Update now?")
	.setButtonUpdateClickListener(...)
	.setButtonDismiss("Maybe later")
	.setButtonDismissClickListener(...)
	.setButtonDoNotShowAgain("Huh, not interested")
	.setButtonDoNotShowAgainClickListener(...)
	.setIcon(R.drawable.ic_update) // Notification icon 
	.setCancelable(false) // Dialog could not be dismissable
	...
```

By default, the "Don't show again" button will be displayed. Use `.setButtonDoNotShowAgain(null)` to hide the button.

## AppUpdaterUtils
The AppUpdaterUtils class works in the same way that the AppUpdater class does, but it won't display any dialog, Snackbar or notification. When using the AppUpdaterUtils class you must provide a custom callback that will be called when the latest version has been checked.

### Using custom callbacks
Adding a callback to the builder allows you to customize what will happen when the latest update has been checked. Keep in mind that when using this method you must be aware of displaying any dialog, snackbar or whatever you want to let the user know that there is a new update available.

```Java
AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
    //.setUpdateFrom(UpdateFrom.AMAZON)
    //.setUpdateFrom(UpdateFrom.GITHUB)
    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
    //...
    .withListener(new AppUpdaterUtils.UpdateListener() {
        @Override
        public void onSuccess(Update update, Boolean isUpdateAvailable) {
            Log.d("Latest Version", update.getLatestVersion());
	    Log.d("Latest Version Code", update.getLatestVersionCode());
	    Log.d("Release notes", update.getReleaseNotes());
	    Log.d("URL", update.getUrlToDownload());
	    Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
        }
        
        @Override
        public void onFailed(AppUpdaterError error) {
            Log.d("AppUpdater Error", "Something went wrong");
        }
     });
appUpdaterUtils.start();
```

![AppUpdater](https://raw.githubusercontent.com/javiersantos/AppUpdater/master/Screenshots/banner.png)

## License
	Copyright 2016 Javier Santos
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
