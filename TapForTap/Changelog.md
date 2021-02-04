
### 1.1.8 / 2012-07-09 

  * Add com.tapfortap.AdMobBanner adapter for AdMob mediation
  * Add AdView.autoRollover for AdMob
  * Scale ads intelligently to fit space
  * Improve detection for tablets
  * Call AdViewListener methods on the UI thread

### 1.1.7 / 2012-06-05

  * Make READ_PHONE_STATE permission optional
  * We do not need the ACCESS_WIFI_STATE permission
  * Mention Android permissions for the manifest in the readme

### 1.1.6 / 2012-05-17

  * Fix a bug when trying to get network info when offline
    (Critical fix for all versions of Android, potential crasher)


### 1.1.5 / 2012-05-16

  * Fix a bug where tapping an ad may not load the URL on a background thread.
    (Critical fix for Android 3.2+, crasher)
  * send network type when filling ads (wifi or cellular)


### 1.1.4 / 2012-05-07

  * only check in once
  * when filling ads encode params in UTF-8


### 1.1.3 / 2012-04-23

  * Fix a bug where images may not be downloaded on a background thread.
    (Critical fix for Android 3.2+, crasher)


### 1.1.2 / 2012-04-18

  * Add AdViewListener for notifications about receiving or failing to receive ads.


### 1.1.1 / 2012-04-13

  * Fix a crash on PhoneGap 1.6.0
  * Fix some potential null pointer exceptions
  * add a changelog
