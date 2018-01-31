package taiwan.no1.app.ssfm.misc.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper

/**
 * Created by weian on 2018/1/6.
 *
 * This class is a controller which shows and controls the states of music player.
 */

class NotificationService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: Notification
    private val binder = NotificationBinder()
    private lateinit var notificationView: RemoteViews
    private var musicPlayerHelper: MusicPlayerHelper? = null

    companion object {
        private const val NOTIFY_ID = 0
        private const val ACTION_PLAY = "ACTION_PLAY"
        private const val ACTION_NEXT = "ACTION_NEXT"
        private const val ACTION_PAUSE = "ACTION_PAUSE"
    }

    fun initNotification() {

        notificationView = RemoteViews(packageName, R.layout.big_notification)
        notificationView.setImageViewResource(R.id.iv_big_album, R.drawable.test_album)
        notificationView.setImageViewResource(R.id.btn_big_next, R.drawable.ic_skip_next)
        notificationView.setImageViewResource(R.id.btn_big_play, R.drawable.ic_play_arrow)
        notificationView.setTextViewText(R.id.tv_big_music_name, "this is big music name")
        notificationView.setTextViewText(R.id.tv_big_singer, "this is big singer name")
        notificationView.setTextViewText(R.id.tv_big_now_time, "00000:00000")
        notificationView.setTextViewText(R.id.tv_big_duration, "00000:00000")

        notification = NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_music_player)
                .setOngoing(true)
                .setCustomBigContentView(notificationView)
                .build()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFY_ID, notification)
    }

    fun removeNotification() {
        notificationManager.cancel(NOTIFY_ID)
    }

    fun setMusicPlayerHelper(helper: MusicPlayerHelper) {
        musicPlayerHelper = helper
    }

    private fun clickPlay() {
        musicPlayerHelper?.play()
    }

    private fun clickNext() {
        musicPlayerHelper?.next()
    }

    private fun clickPause() {
        musicPlayerHelper?.pause()
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when {
            intent?.action == ACTION_PLAY -> clickPlay()
            intent?.action == ACTION_NEXT -> clickNext()
            intent?.action == ACTION_PAUSE -> clickPause()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class NotificationBinder : Binder() {
        fun getService() = this@NotificationService
    }
}
