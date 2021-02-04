# TapForTap Android SDK

Integrating TapForTap into your app is usually really easy. Add our library,
`TapForTap.jar`, to your Android project, add `com.tapfortap.AdView` to
your layout, and then pass in your TapForTap App ID. That's it!

If you are not displaying TapForTap ads then you only need to call
`TapForTap.checkIn(Activity)` once when your app starts up, in the
`onCreate` method of your main activity.

## Instructions

You can view these instructions on the web at
[developer.tapfortap.com/sdk#android](http://developer.tapfortap.com/sdk#android).

**1. Add TapForTap.jar to your project.**

If your project doesn't have a `lib` or `libs` folder create one and
copy `TapForTap.jar` into it.

#### Eclipse

In Eclipse, right-click on your project in the Package Explorer then click
`Properties`.

![Step 1](http://developer.tapfortap.com/images/eclipse-01.png)

In the properties window click `Java Build Path` on the left then click
`Add JARs...` on the right.

![Step 2](http://developer.tapfortap.com/images/eclipse-02.png)

Navigate to the `TapForTap.jar` file you copied into your project earlier then
click `OK`.

![Step 3](http://developer.tapfortap.com/images/eclipse-03.png)

Click `OK` to leave the properties window.


#### IntelliJ IDEA

In IDEA open your Project Structure via the File menu, File &rarr;
Project Structure.

![Step 1](http://developer.tapfortap.com/images/idea-01.png)

Select `Libraries` under `Project Settings` on the left. Add a new Java
library by clicking the plus icon up top, selecting Java, and then selecting
`TapForTap.jar` in your `lib` or `libs` folder.

![Step 2](http://developer.tapfortap.com/images/idea-02.png)

![Step 3](http://developer.tapfortap.com/images/idea-03.png)

IDEA will ask you to select which module to add it to and in most cases you
can click `OK` to add it to the module selected by default.

![Step 4](http://developer.tapfortap.com/images/idea-04.png)

Click OK to leave the Project Structure window.

![Step 5](http://developer.tapfortap.com/images/idea-05.png)

**2. Add com.tapfortap.AdView to your layout.**

    <com.tapfortap.AdView android:id="@+id/ad_view"
                          android:layout_height="50dip"
                          android:layout_width="fill_parent"
                          android:layout_gravity="bottom"
                          />

**3. Pass in your TapForTap App ID.**

This goes in the activity in which you want to display ads.

    // Import TapForTap
    import com.tapfortap.TapForTap;
    import com.tapfortap.AdView;

    // Then pass in your App ID in onCreate
    public class MyActivity extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // Substitute your real App ID here
            TapForTap.setDefaultAppId("<Your TapForTap App ID>");
            TapForTap.checkIn(this);
    
            setContentView(R.layout.main);

            // Now get the AdView and load TapForTap ads!
            AdView adView = (AdView) findViewById(R.id.ad_view);
            adView.loadAds();

            // If you want to remove the ad view later just be sure to call
            // stopLoadingAds() first, e.g. adView.stopLoadingAds()
        }

    }

**4. Add permissions to AndroidManifest.xml**

Add the following permissions to `AndroidManifest.xml`:

    
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

*Note: We use READ_PHONE_STATE to identify devices by their Android ID. You can
choose to omit this permission if you don't want to use it.*

Congratulations! You should now be up and running. Run the app and then
check [developer.tapfortap.com](http://developer.tapfortap.com/) to make sure that
it worked.

**5. Send optional info about your users.**

If you have information about your users that your privacy policy allows you to
share with us, you can help us better target ads by passing it along. Just set
the info on an instance of `com.tapfortap.AdView`. We accept age, gender, and
location data.

    adView.setGender(<MALE or FEMALE>);
    adView.setAge(<age>);
    adView.setLocation(<location>);

Where `gender` is either `MALE` or `FEMALE`, `age` is a positive integer, and `location`
is an `android.location.Location` object.


## Example

Some example code is included to help get you started. Take a look in the
`example` folder to see exactly how it's done.

## API Documentation

To take action when ads are loaded or fail to load you can set a listener on
`AdView` objects. Listeners implement the `AdViewListener` interface which
specifies two methods:

    public void didReceiveAd()
    public void didFailToReceiveAd(String reason)

You can use an anonymous class to set the listener without defining a concrete
class, like so:

    adView.setListener(new AdViewListener() {
        public void didReceiveAd() {
            Log.d("MyActivity", "Tap for Tap ad received");
        }

        public void didFailToReceiveAd(String reason) {
            Log.d("MyActivity", "Tap for Tap failed to receive ad: " + reason);
        }
    });

&copy; Beta Street
