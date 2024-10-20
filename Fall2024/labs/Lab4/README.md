# Lab 4 - Broadcast Receivers

Today’s lab will build off the camera integration code used in the last lab to go deeper into working with Broadcast Receivers, alarms, and notifications.  

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.

#### Background

* Make sure you are familiar with the following sections of the Android developer documentation:
	* [Notifications](https://developer.android.com/training/notify-user/build-notification)
		* Read through the _*Create a basic notification*_ section which should be sufficient for this lab
		* Please note this is an older version of this documentation
	* [Alarms](http://developer.android.com/training/scheduling/alarms.html)
		* Up to the end of Cancel an Alarm
	* [Monitoring battery state](http://developer.android.com/training/monitoring-device-state/battery-monitoring.html)
	* Class overview for [PendingIntent](http://developer.android.com/reference/android/app/PendingIntent.html)
	* Class overview for [BroadcastReceiver](http://developer.android.com/reference/android/content/BroadcastReceiver.html) 


## Introduction

In this lab we will be building a photo taking app that reminds the user to take a photo at regular intervals.  For example this could be the start of an app for a [365 Project](http://365project.org/)-like photography project.

We will also make the app adapt to the battery state of the device to conserve power when the battery is low. In building this app we will learn about Android notifications, alarms, and BroadcastReceivers.


#### Objectives
You are given an Android project containing the starting point code for the lab.  It is a single-```Activity``` app that has a button which dispatches an implicit ```Intent``` to take a photo and then saves the photo (same code used in Lab 3 to take a photo).

Your task is to examine the role broadcast receivers play and how the user can be notified of changes.  We will cover:

* Adding a broadcast receiver component for working with messages within the app
* Creation of a ```Notification```
* Interacting with the Android system to get device status updates

### Create the Alarm

First we'll add the functionality to have an alarm go off at regular intervals to remind the user to take a picture.

**Task 1**

We will need a ```BroadcastReceiver``` class to receive alarms.

1. From the Android view right click on _mobiledev.unb.ca.lab4skeleton_
2. Select New -> Other -> Broadcast Receiver
	* For the class name use __AlarmReceiver__
	* Leave the _Exported_ checkbox option selected
	* Uncheck the _Enabled_ checkbox option 

NOTE:
* This will also add an entry into the ```AndroidManifest.xml``` file
	* You should see the ```receiver``` element inside of the ```application``` element of the file as shown below
		```
			<receiver
				android:name=".AlarmReceiver"
				android:enabled="true"
				android:exported="false" />
		```

**Task 2**

With the Broadcast Receiver in place let's go back and set an alarm.  The alarm should be set to repeat roughly every 60 seconds and should wake the device.

1. Replace the _TODO_ statement in the ```AlarmReceiver```'s ```onReceive``` method with a ```Log``` message in here for now
	* This method will be called when the ```BroadcastReceiver``` receives a broadcast
	* NOTE: Leaving a _TODO_ in code will throw an error when you attempt to run the app

3. Edit the ```AndroidManifest.xml``` file to declare the [appropriate exact alarm permission](https://developer.android.com/training/scheduling/alarms.html#exact-permission-declare)
  * For this lab exercise ```android.permission.SCHEDULE_EXACT_ALARM``` will be sufficient

3. Update the ```MainActivity.onCreate``` method to set an alarm
	* The action of the alarm should be to start ```AlarmReceiver```
	* The following documentation should help here
		* [Alarms](https://developer.android.com/training/scheduling/alarms.html#type) 
		* [PendingIntent](http://developer.android.com/reference/android/app/PendingIntent.html)
	* NOTE:
		* We would typically use alarms for much longer durations
			* For example: for our daily photo app we might set the alarm to run once per day
		* However, this short interval will be useful for testing and debugging

4. Run the app
 	* You should see log messages from ```BroadcastReceiver``` indicating that ```onReceive``` is being called.

### Add Notifications

With the alarm in place we need to update the project to post a system notification which will prompt the user to take another picture.  Throughout this section we will see examples of how different API levels affect the implementation.

**Task 3**

The first step will be to ensure that the correct permissions are in place.

1. Update the ```AndroidManifest.xml``` file to declare the post notifications permission as shown below
	```
		<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
	```
	NOTE:
	* This will prompt the user to allow notifications for Android vesesion 13+ ONLY

2. Open the ```MainActivity``` class and uncomment the call to the function call _checkNotificationPermissions()_
	* Look for a TODO comment inside the ```onCreate``` method

**Task 4**

With the permissions in place let's add the app notifications.

1. Open the ```AlarmReceiver``` class and complete the implementation for the ```onReceive``` method by adding a notification.
	* Follow the _*Create a basic notification*_ section in the [simple notification guide](https://developer.android.com/develop/ui/views/notifications/build-notification#simple-notification) for this
	* The tap action of this notification will be to start ```MainActivity``` (i.e. clicking on the notification takes the user back to the app)
		* Constant values for the channel ID and notification ID can be found in the ```Constants``` file
		* Additional notes for building the notification channel:
			* Set [```setAutoCancel```](http://developer.android.com/reference/android/app/Notification.Builder.html#setAutoCancel%28boolean%29) to ```true``` so that when the user clicks on the notification it is dismissed
			* Set the importance as IMPORTANCE_HIGH
			* The notification channel creation code must be called regardless of the Android level being used; see Notes below
		* Additional details to watch when building the notification itself:
			* Set the small icon to ```R.mipmap.ic_launcher```
			* String values for the notifications can be found in the __strings.xml__ resource file
			* There may be some compiler errors when adding the code to [show the notification](https://developer.android.com/develop/ui/views/notifications/build-notification#notify); to resolve them use the tips provided by Android Studio
	* **TIP:** The methods to create the intents require a context.  Recall that broadcast receivers do not have direct access to the context.  For this have a look at the signature for the _onRecieve_ function.

**NOTES:**
* Android 8.0 (API level 26) introduced a few updates to the way Notifications are handled were added:
	* A channel ID is required for the notification channel.  If you are running against an older version this value will be ignored.
	* The app's notification channel must be registered with the system by passing an instance of ```NotificationChannel``` to ```createNotificationChannel```.
	* The notification channel must be created prior to posting any notifications
	* Best practice is to execute this code as soon as the app starts

2. Run the app again
	* When the alarm fires, you should see the notifications that you have created
	* Notice that the alarm continues to fire even after you have closed the app

### Conserving power

In lecture we've seen how the Android will start killing processes when the battery or other resources are running low.  Having a constantly running alarm task could be a candidate for termination in a low battery state.  

**Task 5**

Android sends an ```ACTION_BATTERY_LOW``` intent when the system changes to a low battery state and an ```ACTION_BATTERY_OKAY``` intent when the battery level is high enough again after previously being low. We will receive these intents to change the behavior of our app.

1. In ```MainActivity``` create a new ```BroadcastReceiver``` called ```batteryInfoReceiver``` and ```@Override``` its ```onReceive``` method similar to the following
```kotlin
private val batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
		}
}
```

**Task 6**

Now we will modify our app to conserve power when the battery is low by disabling the alarm.  If the battery is low turn off the alarm and issue a notification. If the battery state becomes OK, turn the alarm on, and issue a notification.

1. In ```MainActivity.onCreate``` create a new ```IntentFilter``` that includes the actions ```ACTION_BATTERY_LOW``` and ```ACTION_BATTERY_OKAY```
	* Create an [IntentFilter](http://developer.android.com/reference/android/content/IntentFilter.html) and call ```addAction``` to add the appropriate actions to it

2. Register ```batteryInfoReceiver``` so that it will receive any intent that matches the filter you just created
   - Have a look at [registerReceiver](https://developer.android.com/reference/android/content/Context.html#registerReceiver(android.content.BroadcastReceiver,%20android.content.IntentFilter)) for reference

2. Update the  ```batteryInfoReceiver.onReceive()``` method
   * If an ```ACTION_BATTERY_LOW``` intent is received cancel the alarm and show a [Toast](http://developer.android.com/guide/topics/ui/notifiers/toasts.html) message
	 * If an ```ACTION_BATTERY_OKAY``` intent is received set the alarm just like you did previously and show a ```Toast``` indicating which intent was received

4. We dynamically registered ```batteryInfoReceiver``` and so we also need to unregister it to avoid memory leaks.

	* Create a method in ```MainActivity``` called ```onDestroy()``` by including the following code directly after the ```onCreate()``` function.
		```kotlin
		override fun onDestroy() {
				super.onDestroy()
			}
	* Update the function to unregister ```batteryInfoReceiver``` here

#### Note About Testing

In order to test the battery conditions this portion of the lab will be very difficult to test if you are using a physical device.  This due to the fact that you would have to wait for the battery to become low to be able to test if the app responded correctly.
* The recommended approach to test the code is using an Android emulator
* The Android emulator can simulate a device's battery state or location, receiving a text message, etc.
* If you are using a physical device for testing check against the AC power connection as opposed to battery level
  * Have the app cancel the alarm if the AC power is disconnected and set the alarm when the AC power is connected
  * Use ```ACTION_POWER_DISCONNECTED``` instead of ```ACTION_BATTERY_LOW``` and ```ACTION_POWER_CONNECTED``` instead of ```ACTION_BATTERY_OKAY```

## Writeup Task

Create a document with the following items:
	* A screenshot of the toast message when the application enters either a low battery or power disconnected state
	* A screenshot of the toast message when the application leaves either a low battery or power disconnected state
	* A screenshot of the notification reminding the user to tale a picture

## Lab Completion

* In Lab
	* Show the working app running on an emulator to the instructor or TA
* At Home
	* Take screenshots of the application in a either a low battery or power disconnected mode
	* Submit `MainActivity.kt`, `AlarmReceiver.kt`, `AndroidManifest.xml`, and your document with screenshots to the Lab4 drop box folder on D2L
* Keep a copy of your project work and answers for future reference
