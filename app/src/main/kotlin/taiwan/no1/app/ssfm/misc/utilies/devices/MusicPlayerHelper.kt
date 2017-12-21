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

    private val durationChangedListeners = mutableListOf<(Int) -> Unit>()
    private val currentTimeListeners = mutableListOf<(Int) -> Unit>()
    private val bufferPercentageListeners = mutableListOf<(Int) -> Unit>()
    private val stateChangedListeners = mutableListOf<(MusicPlayerState) -> Unit>()
    private lateinit var player: IMusicPlayer
    private lateinit var musicUri: String

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun hold(player: IMusicPlayer) {
        this.player = player
        setPlayerListener()
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

    //region MusicPlayer collection operations.
    fun addDurationChangedListeners(listener: (Int) -> Unit) = durationChangedListeners.add(listener)

    fun addBufferPercentageListeners(listener: (Int) -> Unit) = bufferPercentageListeners.add(listener)

    fun addCurrentTimeListeners(listener: (Int) -> Unit) = currentTimeListeners.add(listener)

    fun addStateChangedListeners(listener: (MusicPlayerState) -> Unit) = stateChangedListeners.add(listener)

    fun removeDurationChangedListeners(listener: (Int) -> Unit) = durationChangedListeners.remove(listener)

    fun removeBufferPercentageListeners(listener: (Int) -> Unit) = bufferPercentageListeners.remove(listener)

    fun removeCurrentTimeListeners(listener: (Int) -> Unit) = currentTimeListeners.remove(listener)

    fun removeStateChangedListeners(listener: (MusicPlayerState) -> Unit) = stateChangedListeners.remove(listener)

    fun removeAllDurationChangedListeners() = durationChangedListeners.clear()

    fun removeAllBufferPercentageListeners() = bufferPercentageListeners.clear()

    fun removeAllCurrentTimeListeners() = currentTimeListeners.clear()

    fun removeAllStateChangedListeners() = stateChangedListeners.clear()

    fun clearAllListener() {
        removeAllDurationChangedListeners()
        removeAllBufferPercentageListeners()
        removeAllCurrentTimeListeners()
        removeAllStateChangedListeners()
    }
    //endregion

    private fun setPlayerListener() {
        player.setEventListener(PlayerEventListenerImpl {
            onDurationChanged = { duration -> durationChangedListeners.forEach { it.invoke(duration) } }
            onCurrentTime = { second -> currentTimeListeners.forEach { it.invoke(second) } }
            onBufferPercentage = { percent -> bufferPercentageListeners.forEach { it.invoke(percent) } }
            onPlayerStateChanged = { state -> stateChangedListeners.forEach { it.invoke(state) } }
        })
    }
}
