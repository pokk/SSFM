package taiwan.no1.app.ssfm.misc.utilies.devices

import weian.cheng.mediaplayerwithexoplayer.ExoPlayerEventListener.PlayerEventListenerImpl
import weian.cheng.mediaplayerwithexoplayer.IMusicPlayer
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Pause
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Play
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Standby

/**
 * @author  jieyi
 * @since   2017/12/02
 */
class MusicPlayerHelper private constructor() {
    private object Holder {
        val INSTANCE = MusicPlayerHelper()
    }

    var durationChanged: ((Int) -> Unit) = {}
        set(value) {
            setPlayerListener()
        }
    var currentTime: ((Int) -> Unit) = {}
        set(value) {
            setPlayerListener()
        }
    var bufferPrecentage: ((Int) -> Unit) = {}
        set(value) {
            setPlayerListener()
        }
    var stateChanged: ((MusicPlayerState) -> Unit) = {}
        set(value) {
            setPlayerListener()
        }
    private lateinit var player: IMusicPlayer
    private lateinit var musicUri: String
    private var playerListener: PlayerEventListenerImpl? = null

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun hold(player: IMusicPlayer) {
        this.player = player
    }

    fun play(uri: String, callback: ((state: MusicPlayerState) -> Unit)? = null) {
        if (::musicUri.isInitialized && uri == musicUri) {
            when {
                isPlaying() -> player.pause()
                isPause() -> player.resume()
                isStop() -> {
                    player.seekTo(0)
                    callback?.invoke(getState())
                }
            }
        }
        else {
            if (::player.isInitialized && isPlaying()) player.stop()
            player.play(uri)
            callback?.invoke(getState())
            musicUri = uri
        }
    }

    fun getState() = player.getPlayerState()

    fun getCurrentUri() = if (::musicUri.isInitialized) musicUri else "None"

    fun isPlaying() = Play == getState()

    fun isPause() = Pause == getState()

    fun isStop() = Standby == getState()

    private fun setPlayerListener() {
        releasePlayerListener()
        playerListener = PlayerEventListenerImpl {
            onDurationChanged = durationChanged
            onBufferPercentage = bufferPrecentage
            onCurrentTime = currentTime
            onPlayerStateChanged = stateChanged
        }
        player.setEventListener(playerListener ?: PlayerEventListenerImpl { })
    }

    private fun releasePlayerListener() {
        playerListener = null
        // OPTIMIZE(jieyi): 2017/12/21 Hope here can be released.
        // playerListener.release()
    }
}
