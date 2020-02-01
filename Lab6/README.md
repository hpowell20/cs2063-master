# Lab 6 - Broadcast Receivers

Today’s lab will build off the camera integration code used in the last lab to go deeper into working with Broadcast Receivers, alarms, and notifications.  

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.

**To receive credit for this lab you must pair program.**

**Switch between the roles of navigator and driver every 10 to 15
minutes.**

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

Create a ```BroadcastReceiver``` to receive alarms.

1. Add a new Java file called ```AlarmReceiver.java``` which extends ```BroadcastReceiver```
2. Override ```AlarmReceiver```'s ```onReceive``` method
	* This method will be called when the ```BroadcastReceiver``` receives a broadcast
	* Add a ```Log``` message in here for now

**Task 2**

With the ```BroadcastReceiver``` component added to our application it needs to be registered in the ```AndroidManifest.xml``` file.  

1. Add the following ```receiver``` element inside of the ```application``` element of the file:
```
        <receiver android:name=".AlarmReceiver"/>
```

**Task 3**

With the Broadcast Receiver in place let's go back and set an alarm.  The alarm should be set to repeat roughly every 60 seconds and should wake the device.

1. Update the ```MainActivity.onCreate``` method to set an alarm
	* The action of the alarm should be to start ```AlarmReceiver```
		* The documentation on [alarms](http://developer.android.com/training/scheduling/alarms.html) and [PendingIntent](http://developer.android.com/reference/android/app/PendingIntent.html) should help here
		* NOTE:
			* We would typically use alarms for much longer durations. For example: for our daily photo app we might set the alarm to run once per day.
			* However, this short interval will be useful for testing and debugging

2. Run the app
 	* You should see log messages from ```BroadcastReceiver``` indicating that ```onReceive``` is being called.


## Add Notifications ##

As of Android 8.0 (API level 26) a few updates to the way Notifications are handled were added:
* When setting the notification content a channel ID is required.  If you are running against an older version this value will be ignored.
* The app's notification channel must be registered with the system by passing an instance of ```NotificationChannel``` to ```createNotificationChannel```.
	* The notification channel must be created prior to posting any notifications
	* Best practice is to execute this code as soon as the app starts

**Task 4**
With the alarm in place we need to update the project to display a Toast notification which will prompt the user to take another picture.

1. Open the ```AlarmReceiver``` class and complete the implementation for the ```onReceive``` method by adding a notification.
	* Follow the _*Create a basic notification*_ section in the [guide](https://developer.android.com/training/notify-user/build-notification#SimpleNotification) for this
	* The action of this notification will be to start ```MainActivity``` (i.e. clicking on the notification takes the user back to the app)
		* Additional details to watch when building your notification:
			* Set the small icon to ```R.mipmap.ic_launcher```
			* Set [```setAutoCancel```](http://developer.android.com/reference/android/app/Notification.Builder.html#setAutoCancel%28boolean%29) to ```true``` so that when the user clicks on the notification it is dismissed
			* Set the importance as IMPORTANCE_HIGH

2. Run the app again
	* When the alarm fires, you should see the notifications that you have created
	* Notice that the alarm continues to fire even after you have closed the app

### Conserving power


Details of the alarm:
When an alarm is received, if the activity is open, have it display a
Toast notification to take another picture. If it is closed, then
create a Notification in the Notification window; when the user
selects that it takes them back to the original app.

Also create a BroadcastReceiver to monitor the state of the
battery. If the battery is low, turn off the alarm and issue a
notification. If the battery state becomes OK, turn the alarm on, and
issue a notification.

Now we will modify our app to conserve power when the battery is low by disabling the alarm.  

**Task 5 - Check Battery State**
Android sends an ```ACTION_BATTERY_LOW``` intent when the system changes to a low battery state, and an ```ACTION_BATTERY_OKAY``` intent when the battery level is high enough again after previously being low. We will receive these intents to change the behavior of our app.

In ```MainActivity``` create a new ```BroadcastReceiver``` called ```batteryInfoReceiver``` and ```@Override``` its ```onReceive``` method. This will look like this:

```
private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
};
```

**Task 6**
In ```MainActivity.onCreate``` create a new ```IntentFilter``` that includes the actions ```ACTION_BATTERY_LOW``` and ```ACTION_BATTERY_OKAY```.  Register ```batteryInfoReceiver``` so that it will receive any intent that matches the filter you just created.

Hint: Create an [```IntentFilter```](http://developer.android.com/reference/android/content/IntentFilter.html) and call ```addAction``` to add the appropriate actions to it.

Hint: [```registerReceiver```](http://developer.android.com/reference/android/content/Context.html#registerReceiver%28android.content.BroadcastReceiver,%20android.content.IntentFilter%29)


Now implement ```batteryInfoReceiver.onReceive()```. If an ```ACTION_BATTERY_LOW``` intent is received, cancel the alarm.  If an ```ACTION_BATTERY_OKAY``` intent is received, set the alarm just like you did previously. Also show a ```Toast``` indicating which intent was received.

We dynamically registered ```batteryInfoReceiver``` and so we also need to unregister it to avoid memory leaks. ```@Override``` ```MainActivity.onDestroy()``` and unregister ```batteryInfoReceiver``` here.

#### Note about testing

This portion of the lab will be very difficult to test if you are using a physical device. This is due to the fact that you would have to wait for the battery to become low to be able to test if the app responded correctly.  The recommended approach to test the code is using an Android emulator (which you might have installed on your own computer, but is not available on the lab machines).  The Android emulator allows you to easily simulate a device's battery state or location, receiving a text message, etc.

For the purposes of this lab, if you are testing on a physical device, instead have the app cancel the alarm if the AC power is disconnected, and set the alarm when the AC power is connected. To do this, just use ```ACTION_POWER_DISCONNECTED``` instead of ```ACTION_BATTERY_LOW``` and ```ACTION_POWER_CONNECTED``` instead of ```ACTION_BATTERY_OK```.  You will not be able to test this portion of the lab on the Android VM on the computers in the lab.


**Lab Completion**

* Show the working app running on an emulator to the instructor or TA
* Keep a copy of your project work and answers for future reference
