package taiwan.no1.app.ssfm.misc.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper

/**
 * Created by weian on 2018/1/6.
 *
 * This class is a controller which shows and controls the states of music player.
 */

class NotificationService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: Notification.Builder
    private lateinit var notification: Notification
    private val binder = NotificationBinder()
    private var musicPlayerHelper: MusicPlayerHelper? = null

    private val PLAYER_ICON = R.drawable.ic_music_player
    private val PLAYER_ICON_PLAY = R.drawable.ic_play_arrow
    private val PLAYER_ICON_PAUSE = R.drawable.ic_pause
    private val PLAYER_ICON_FORWARD = R.drawable.ic_skip_next

    private lateinit var actionPlay: Notification.Action
    private lateinit var actionPause: Notification.Action
    private lateinit var actionNext: Notification.Action

    companion object {
        private const val NOTIFY_ID = 0
        private const val ACTION_PLAY = "ACTION_PLAY"
        private const val ACTION_NEXT = "ACTION_NEXT"
        private const val ACTION_PAUSE = "ACTION_PAUSE"
    }

    init {
        RxBus.get().register(this)
    }

    fun initNotification() {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // action play
        val intentPlay = Intent(this, NotificationService::class.java)
        intentPlay.action = ACTION_PLAY
        val pendingIntentPlay = PendingIntent.getService(this, 0, intentPlay, 0)
        actionPlay = Notification.Action.Builder(PLAYER_ICON_PLAY, "Play", pendingIntentPlay).build()

        // action pause
        val intentPause = Intent(this, NotificationService::class.java)
        intentPause.action = ACTION_PAUSE
        val pendingIntentPause = PendingIntent.getService(this, 0, intentPause, 0)
        actionPause = Notification.Action(PLAYER_ICON_PAUSE, "Pause", pendingIntentPause)

        // action forward
        val intentForward = Intent(this, NotificationService::class.java)
        intentForward.action = ACTION_NEXT
        val pendingIntentForward = PendingIntent.getService(this, 0, intentForward, 0)
        actionNext = Notification.Action(PLAYER_ICON_FORWARD, "Next", pendingIntentForward)

        notificationBuilder = Notification.Builder(applicationContext)
                .setSmallIcon(PLAYER_ICON)
                .setContentTitle(applicationContext.packageName)
                .addAction(actionPlay)
                .addAction(actionNext)

        notificationManager.notify(NOTIFY_ID, notificationBuilder.build())
    }

    fun removeNotification() {
        notificationManager.cancel(NOTIFY_ID)
        RxBus.get().unregister(this)
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
