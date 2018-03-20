package taiwan.no1.app.ssfm.misc.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.support.v4.app.NotificationCompat
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper

/**
 * Created by weian on 2018/1/6.
 *
 * This class is a controller which shows and controls the states of music player.
 */

class NotificationService : Service() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notification: NotificationCompat
    private val binder = NotificationBinder()
    private var musicPlayerHelper: MusicPlayerHelper? = null

    private val PLAYER_ICON = R.drawable.ic_music_player
    private val PLAYER_ICON_PLAY = R.drawable.ic_play_arrow
    private val PLAYER_ICON_PAUSE = R.drawable.ic_pause
    private val PLAYER_ICON_FORWARD = R.drawable.ic_skip_next

    private val actionPlay by lazy {
        val intentPlay = Intent(this, NotificationService::class.java).apply { action = ACTION_PLAY }
        val pendingIntentPlay = PendingIntent.getService(this, 0, intentPlay, 0)

        NotificationCompat.Action.Builder(PLAYER_ICON_PLAY, "Play", pendingIntentPlay).build()
    }
    private val actionPause by lazy {
        val intentPause = Intent(this, NotificationService::class.java).apply { action = ACTION_PAUSE }
        val pendingIntentPause = PendingIntent.getService(this, 0, intentPause, 0)

        NotificationCompat.Action(PLAYER_ICON_PAUSE, "Pause", pendingIntentPause)
    }
    private val actionNext by lazy {
        val intentForward = Intent(this, NotificationService::class.java).apply { action = ACTION_NEXT }
        val pendingIntentForward = PendingIntent.getService(this, 0, intentForward, 0)

        NotificationCompat.Action(PLAYER_ICON_FORWARD, "Next", pendingIntentForward)
    }

    companion object {
        private const val NOTIFY_ID = 0
        private const val ACTION_PLAY = "ACTION_PLAY"
        private const val ACTION_NEXT = "ACTION_NEXT"
        private const val ACTION_PAUSE = "ACTION_PAUSE"
    }

    fun initNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationBuilder = NotificationCompat.Builder(applicationContext, "test")
            .setSmallIcon(PLAYER_ICON)
            .setContentTitle(applicationContext.packageName.split(".").last())
            .addAction(actionPlay)
            .addAction(actionNext)
            .setOngoing(true)

        notificationManager.notify(NOTIFY_ID, notificationBuilder.build())
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

    override fun onBind(intent: Intent?) = binder

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
