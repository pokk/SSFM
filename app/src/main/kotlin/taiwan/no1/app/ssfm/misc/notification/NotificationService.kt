package taiwan.no1.app.ssfm.misc.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import taiwan.no1.app.ssfm.R

/**
 * Created by weian on 2018/1/6.
 *
 * This class is a controller which shows and controls the states of music player.
 */

class NotificationService : Service() {

    companion object {
        private const val ACTION_PLAY = "ACTION_PLAY"
        private const val ACTION_PAUSE = "ACTION_PAUSE"
        private const val ACTION_NEXT = "ACTION_NEXT"
    }

    private var actionPlay: NotificationCompat.Action
    private var actionPause: NotificationCompat.Action
    private var actionNext: NotificationCompat.Action

    private val ICON_MUSIC_PLAYER = R.drawable.ic_music_player
    private val ICON_PLAY = R.drawable.ic_play_arrow
    private val ICON_PAUSE = R.drawable.ic_pause
    private val ICON_NEXT = R.drawable.ic_skip_next

    private val appName = "SSFM"
    private val notifyID = 0
    private val binder: IBinder = NotificationBinder()
    private var manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var notificationBuilder: NotificationCompat.Builder
    private var buttonEventListener: ButtonEvent? = null

    init {

        // action play
        val intentPlay = Intent(this, NotificationService::class.java)
        intentPlay.action = ACTION_PLAY
        val pendingIntentPlay = PendingIntent.getService(this, 0, intentPlay, 0)
        actionPlay = NotificationCompat.Action(ICON_PLAY, "Play", pendingIntentPlay)

        // action pause
        val intentPause = Intent(this, NotificationService::class.java)
        intentPause.action = ACTION_PAUSE
        val pendingIntentPause = PendingIntent.getService(this, 0, intentPause, 0)
        actionPause = NotificationCompat.Action(ICON_PAUSE, "Pause", pendingIntentPause)

        // action forward
        val intentForward = Intent(this, NotificationService::class.java)
        intentForward.action = ACTION_NEXT
        val pendingIntentForward = PendingIntent.getService(this, 0, intentForward, 0)
        actionNext = NotificationCompat.Action(ICON_NEXT, "Next", pendingIntentForward)

        notificationBuilder = NotificationCompat.Builder(applicationContext).
            setSmallIcon(ICON_MUSIC_PLAYER).
            setContentTitle(appName).
            addAction(actionPause).
            addAction(actionNext)

        manager.notify(notifyID, notificationBuilder.build())
    }

    private fun clickMusicPlayerPlay() {
        notificationBuilder.mActions.clear()
        notificationBuilder.addAction(actionPause)
        notificationBuilder.addAction(actionNext)
        notificationBuilder.setContentText(buttonEventListener?.onPlay())
        manager.notify(notifyID, notificationBuilder.build())
    }

    private fun clickMusicPlayerPause() {
        notificationBuilder.mActions.clear()
        notificationBuilder.addAction(actionPlay)
        notificationBuilder.addAction(actionNext)
        manager.notify(notifyID, notificationBuilder.build())
    }

    private fun clickMusicPlayerNext() {
        notificationBuilder.mActions.clear()
        notificationBuilder.addAction(actionPause)
        notificationBuilder.addAction(actionNext)
        notificationBuilder.setContentText(buttonEventListener?.onNext())
        manager.notify(notifyID, notificationBuilder.build())
    }

    fun play() {
        clickMusicPlayerPlay()
    }

    fun pause() {
        clickMusicPlayerPause()
    }

    fun removeNotification() {
        manager.cancel(notifyID)
    }

    fun setButtonEventListener(buttonEventListener: ButtonEvent) {
        this.buttonEventListener = buttonEventListener
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when {
            intent?.action.equals(ACTION_PLAY) -> clickMusicPlayerPlay()
            intent?.action.equals(ACTION_PAUSE) -> clickMusicPlayerPause()
            intent?.action.equals(ACTION_NEXT) -> clickMusicPlayerNext()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class NotificationBinder : Binder() {
        fun getService() = this@NotificationService
    }
}
