package com.loyaltyworks.keshavcement.utils.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.loyaltyworks.keshavcement.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@RequiresApi(Build.VERSION_CODES.N)
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var SCREENING = "ActionType"
    private var IMAGE_URL = "image_url"
    private var PUSH_USER_ACTION_ID = "PushUserActionID"
    private var ID = "id"
    private var TITLE = "title"
    private var BIG_TEXT = "big_text"


    private var notifyID = 1
    private var CHANNEL_ID = "Keshav_001" // The id of the channel.

    var name: CharSequence = "Keshav Cement" // The user-visible name of the channel.

    var importance = NotificationManager.IMPORTANCE_HIGH


    //https://rukminim1.flixcart.com/image/832/832/j9hdn680/soap/a/z/h/1-50-rich-moisture-bathing-bar-baby-dove-original-imaez6hxhby6z8du.jpeg
    /*
    * Get Unique device token .....
    * */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // AppController.getInstance().sharedStorage(MyFirebaseMessagingService.this, Constants.PUSH_TOKEN,token);
//        AppController.getInstance().printLog("Push TOKEN : ", "Refreshed token: $token")
    }

    /*
    * On receive push notification :
    * */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        AppController.getInstance().printLog(javaClass.simpleName, "onMessageReceived: " + remoteMessage.data)
        sendNotification(remoteMessage.data)
    }


    fun sendNotification(data: Map<String?, String?>) {
        Log.d(javaClass.simpleName, "NOTIFICATION_DATA: " + Gson().toJson(data))
//        AppController.getInstance().printLog("TEST NOTIFICATION", "sendNotification: ")
        var big_bitmap_image: Bitmap? = null
//        val intent = Intent(this, NotificationActivity::class.java)
//        intent.putExtra(SCREENING, data[SCREENING])
//        intent.putExtra(ID, data[ID])
//        intent.putExtra(PUSH_USER_ACTION_ID, data[PUSH_USER_ACTION_ID])
//        intent.action = SCREENING
//        intent.action = ID
//        intent.action = PUSH_USER_ACTION_ID
//        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val image = data[IMAGE_URL]

        /**
         * PushNotificationForCatalogue = 100,
         * PushNotificationForPromotion = 101,
         * PushNotificationForSMSPromotion = 102,
         * PushNotificationForCustomerQuery = 103,
         * PushNotificationForSms = 104
         *
         * */
        if (data[IMAGE_URL] != null && !data[IMAGE_URL]!!.isEmpty()) {
            val imagePath: String?
            if (data[SCREENING]!!.toInt() == 100) { //For Catalogue
                imagePath = data[IMAGE_URL]
                big_bitmap_image = getBitmapFromURL(imagePath)
            } else if ((data[SCREENING] ?: error("")).toInt() == 101) {
                imagePath = data[IMAGE_URL]
                big_bitmap_image = getBitmapFromURL(imagePath)
            } else if ((data[SCREENING] ?: error("")).toInt() == 102) {
                imagePath = data[IMAGE_URL]
                big_bitmap_image = getBitmapFromURL(imagePath)
            } else if ((data[SCREENING] ?: error("")).toInt() == 103) {
                imagePath = data[IMAGE_URL]
                big_bitmap_image = getBitmapFromURL(imagePath)
            }
        }

//        val intent = Intent(this, SplashScreenActivity::class.java)
//        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Apply the layouts to the notification
        val mBuilder = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(data[TITLE])
            .setContentText(data[BIG_TEXT])
            .setAutoCancel(true)
            .setChannelId(CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pIntent)
        if (big_bitmap_image != null) {
            mBuilder.setLargeIcon(big_bitmap_image)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(big_bitmap_image)
                )
        }
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //Android Oreo onwards , NotificationChannel is required to add in NotificationCompat constructor..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(notifyID /* ID of notification */, mBuilder.build())
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }


}