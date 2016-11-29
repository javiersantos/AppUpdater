<h1 align="center">AppUpdater <a href="https://github.com/javiersantos/AppUpdater#how-to-include"><img src="https://jitpack.io/v/javiersantos/AppUpdater.svg"></a></h1>
<h4 align="center">Android Library</h4>

<p align="center">
  <a target="_blank" href="https://android-arsenal.com/api?level=8"><img src="https://img.shields.io/badge/API-8%2B-orange.svg"></a>
  <a target="_blank" href="https://travis-ci.org/javiersantos/AppUpdater"><img src="https://travis-ci.org/javiersantos/AppUpdater.svg?branch=master"></a>
  <a target="_blank" href="http://android-arsenal.com/details/1/3094"><img src="https://img.shields.io/badge/Android%20Arsenal-AppUpdater-blue.svg"></a>
  <a target="_blank" href="https://www.paypal.me/javiersantos" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
</p>

<p align="center">Android Library that checks for updates on Google Play, GitHub, Amazon, F-Droid or your own server. This library notifies your apps' updates by showing a Material dialog, Snackbar or notification. Check out the <a href="https://github.com/javiersantos/AppUpdater/wiki">wiki</a>.</p>

## Sample Project
You can download the latest sample APK from Google Play:

<a target="_blank" href="https://play.google.com/store/apps/details?id=com.github.javiersantos.appupdater.demo"><img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="60"></a>

## How to include
Add the repository to your project **build.gradle**:
```Javascript
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

And add the library to your module **build.gradle**:
```Javascript
dependencies {
    compile 'com.github.javiersantos:AppUpdater:2.4'
}
```

## Usage
Add **INTERNET** and **ACCESS_NETWORK_STATE** permissions to your app's Manifest:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

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

When using the Snackbar, you can change the duration by using `.setDuration(Duration.NORMAL)` (default) or 
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

When using the XML source you must upload a XML file somewhere on the Internet following the structure explained in the [wiki](https://github.com/javiersantos/AppUpdater/wiki/UpdateFrom.XML) and add the URL as shown in this example: `.setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.xml")`.

When using the JSON source you must upload a JSON file somewhere on the Internet following the structure as shown in the example [file](https://raw.githubusercontent.com/kgritesh/AppUpdater/feature-json-updater/app/update-changelog.json) and add the URL as shown in this example: `.setUpdateJSON("https://raw.githubusercontent.com/kgritesh/AppUpdater/feature-json-updater/app/update-changelog.json")`.

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
	.setButtonDismiss("Maybe later")
	.setButtonDoNotShowAgain("Huh, not interested")
	.setIcon(R.drawable.ic_update) // Notification icon 
	...
```

## Other features
### Get the latest update and compare with the installed one (asynchronous)
```Java
AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
    //.setUpdateFrom(UpdateFrom.AMAZON)
    //.setUpdateFrom(UpdateFrom.GITHUB)
    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
    //...
    .withListener(new AppUpdaterUtils.UpdateListener() {
        @Override
        public void onSuccess(Update update, Boolean isUpdateAvailable) {
            Log.d("AppUpdater", update.getLatestVersion() + ", " + update.getUrlToDownload() + ", " + Boolean.toString(isUpdateAvailable));
        }
        
        @Override
        public void onFailed(AppUpdaterError error) {
            Log.d("AppUpdater", "Something went wrong");
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
