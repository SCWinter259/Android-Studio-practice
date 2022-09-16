# Activities

## The concept of Activities

A user's interaction with the app doesn't always begin in the same place. For instance, if you open an email app from your home screen, you might see a list of email. By contrast, if you are using a social media app that then launches your email app, you might go directly to the email app's screen for composing an email.

The `Activity` class is designed for this. When one app invokes another, the calling app invokes an activity in the other app, rather than the app as an atomic whole. The activity serves as an entry point for an app's interaction with the user, and can be implemented as a subclass of the `Activity` class.

An activity provides the window in which the app draws its UI. This window typically fill the screen, but may be smaller than the screen and float on top of other windows. Generally, one activity implements one screen in an app. For instance, one of the app's activities may implement a Preferences screen, while another activity implements a Select Photo screen.

Typically, one activity in an app is specified as the **main activity**, which is the first screen to appear when the user launches the app. Each activity can then start another activity in order to perform different actions.

Although activities work together to form a cohesive user experience in an app, each activity is loosely bound to the other activities. In fact, activities often sart up activities belonging to other apps. For example, a browser app might launch the Share activity of a social media app.

## Configuring the manifest

For your app to be able to use activities, you must declare the activities, and certain of their attributes, in the manifest. to do so, open Manifest file and add an `<activity>` element as a child of the `<application>` element:
```xml
<manifest ... >
  <application ... >
      <activity android:name=".ExampleActivity" />
      ...
  </application ... >
  ...
</manifest >
```
`Intent filters` provide the ability to launch *explicit* and *implicit request*. For example, an explicit request might tell the system to "Start the Send Email activity in the Gmail app". By contrast, an implicit request tells the system to “Start a Send Email screen in any activity that can do the job." When the system UI asks a user which app to use in performing a task, that’s an intent filter at work.

You can declare an `<intent-filter>` attribute in the `<activity>` element. The definition of this element includes an `<action>` element and, optionally, a `<category>` element and/or a `<data>` element. For example, the following code snippet shows how to configure an activity that sends text data, and receives requests from other activities to do so:
```xml
<activity android:name=".ExampleActivity" android:icon="@drawable/app_icon">
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="text/plain" />
    </intent-filter>
</activity>
```
The `<action>` element specifies that this activity sends data. Declaring the `<category>` element as `DEFAULT` enables the activity to receive launch requests. The `<data>` element specifies the type of data that this activity can send. The following code snippet shows how to call the activity described above:
```java
// Create the text message with a string
Intent sendIntent = new Intent();
sendIntent.setAction(Intent.ACTION_SEND);
sendIntent.setType("text/plain");
sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
// Start the activity
startActivity(sendIntent);
```
You can use the manifest's `<activity>` tag to control which apps can start a particular activity. a parent activity cannot launch a child activity unless both activities have the same permissions in their manifest. If you declare a `<uses-permission>` element for a parent activity, each child activity must have a matching `<uses-permission>` element.

For example, if your app wants to use a hypothetical app named SocialApp to share a post on social media, SocialApp itself must define the permission that an app calling it must have:
```xml
<manifest>
<activity android:name="...."
   android:permission=”com.google.socialapp.permission.SHARE_POST” 
/>
```
Then, to be allowed to call SocialApp, your app must match the permission set in SocialApp's manifest:
```xml
<manifest>
   <uses-permission android:name="com.google.socialapp.permission.SHARE_POST" />
</manifest>
```
An example of this is when you are reading on CBC app and you want to share the news onto Facebook. The CBC app will bring you to Facebook.

## Managing the activity lifecycle
- `onCreate()`: This one fires when the system calls your activity. Literally the beginning of the whole app. Your app should create views and bind data to lists here.
- `onStart()`: The activity enters Started state and becomes visible to the user.
- `onResume()`
- `onPause()`
- `onStop()`
- `onRestart()`
- `onDestroy()`

*I currently have no intention of continuing writing this part. Basic methods are listed, the rest is Google search*

![Image](https://developer.android.com/guide/components/images/activity_lifecycle.png)