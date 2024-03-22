package com.example.chika

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage

const val channelId ="notification_channel"
const val channelName ="com.example.chika"
class MyFirebaseInstanceIDService: FirebaseMessagingService() {
    //show the notification
    override fun onMessageReceived(message: RemoteMessage) {
        if(message.notification!=null){
            generateNotification(message.notification!!.title!!,message.notification!!.body!!)
        }
    }
    //Generate Notifications
    private fun generateNotification(title:String,message:String){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

    //channel id,channel name
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.donut)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setVibrate(longArrayOf(1000,1000,1000,1000)) // 1000 is the time in millisecond and 1000 for vibration, 1000 for relaxation time
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(123,builder.build())
    }
    //custom layout notification
    private fun getRemoteView(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews("com.example.chika",R.layout.notification_layout)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.Description,message)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.donut)
        return remoteView
    }
}