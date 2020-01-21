# Lab 6 - Broadcast Receivers

Today’s lab will build off the camera integration code used in the last lab to go deeper into working with Broadcast Recievers and sending notifications.

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.

**To receive credit for this lab you must pair program.**

**Switch between the roles of navigator and driver every 10 to 15
minutes.**

#### Background

* Make sure you are familiar with the following sections of the Android developer documentation:
	* [Notifications](https://web.archive.org/web/20160303170900/https://developer.android.com/guide/topics/ui/notifiers/notifications.html)
		* Read through the _*Creating a simple notification*_ section which should be sufficient for this lab
		* Please note this is an older version of this documentation
		* Android has introduced the concept of a ```NotificationChannel``` which will discuss in further detail in a later lecture and will not be used in this lab
	* [Alarms](http://developer.android.com/training/scheduling/alarms.html)
		* Up to the end of Cancel an Alarm
	* [Monitoring battery state](http://developer.android.com/training/monitoring-device-state/battery-monitoring.html)
	* [PendingIntents](http://developer.android.com/reference/android/app/PendingIntent.html)
		* Class overview section will be sufficient
	* [BroadcastReceivers](http://developer.android.com/reference/android/content/BroadcastReceiver.html)
		* Class overview section will be sufficient


## Introduction

In this lab we will be building a photo taking app that reminds the user to take a photo at regular intervals.  For example this could be the start of an app for a [365 Project](http://365project.org/)-like photography project.

We will also make the app adapt to the battery state of the device to conserve power when the battery is low. In building this app we will learn about Android notifications, alarms, and BroadcastReceivers.


#### Objectives
You are given an Android project containing the starting point code for the lab.  It is a single-```Activity``` app that has a button which dispatches an implicit ```Intent``` to take a photo and then saves the photo (same code used in Lab 3 to take a photo).

Your task is to examine the role broadcast receivers play and how the user can be notified of changes.  We will cover:

* Adding a broadcast receiver component for working with messages within the app
* Creation of a ```Notification```
* Interacting with the Android system to get device status updates

### Notifications

First we'll add the functionality to have an alarm go off at regular intervals to remind the user to take a picture.

**Task 1**

The first step is create a ```BroadcastReceiver``` to receive an alarm.

1. Create a new Java file called ```AlarmReceiver.java``` which extends ```BroadcastReceiver```
2. Override ```AlarmReceiver```'s ```onReceive``` method
	* This method will be called when the ```BroadcastReceiver``` receives a broadcast
	* Simply add ```Log``` message in here for now

**Task 2**
With the ```BroadcastReceiver``` component added to our application it needs to be registered in the ```AndroidManifest.xml``` file.  

1. This can be accomplished by adding the following ```receiver``` element inside of the ```application``` element of the file:

```
        <receiver android:name=".AlarmReceiver"/>
```

In ```MainActivity.onCreate``` set an alarm. The alarm should repeat roughly every 60 seconds, and should wake the device. The action of the alarm should be to start ```AlarmReceiver```.  The documentation on [alarms](http://developer.android.com/training/scheduling/alarms.html) will help here.

(We would typically use alarms for much longer durations. For example: for our daily photo app we might set the alarm to run once per day. However, this short interval will be useful for testing and debugging. It would be quite frustrating to have to wait a day to determine if the alarm code you wrote is working properly...)

At this point you can run your app, and you should see log messages from ```BroadcastReceiver``` indicating that ```onReceive``` is being called.

**Task 3 - Add Notification**
Now let's go back to ```AlarmReceiver``` and finish implementing ```onReceive```. Follow the _*Creating a simple notification*_ section in the [guide](https://web.archive.org/web/20160303170900/https://developer.android.com/guide/topics/ui/notifiers/notifications.html#SimpleNotification) for this.

The action of this notification will be to start ```MainActivity``` (i.e. clicking on the notification takes the user back to the app).  When building your notification, you can set the small icon to ```R.mipmap.ic_launcher```, and you should set [```setAutoCancel```](http://developer.android.com/reference/android/app/Notification.Builder.html#setAutoCancel%28boolean%29) to ```true``` so that when the user clicks on the notification it is dismissed.

Now you can run your app. When the alarm fires, you should see the notifications that you have created. Notice that the alarm continues to fire even after you have closed the app.

### Conserving power

Now we will modify our app to conserve power when the battery is low by disabling the alarm.  

**Task 4 - Check Battery State**
Android sends an ```ACTION_BATTERY_LOW``` intent when the system changes to a low battery state, and an ```ACTION_BATTERY_OKAY``` intent when the battery level is high enough again after previously being low. We will receive these intents to change the behavior of our app.

In ```MainActivity``` create a new ```BroadcastReceiver``` called ```batteryInfoReceiver``` and ```@Override``` its ```onReceive``` method. This will look like this:

```
private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
};
```

**Task 5**
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
