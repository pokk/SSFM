package taiwan.no1.app.ssfm.misc.utilies.devices

import weian.cheng.mediaplayerwithexoplayer.ExoPlayerWrapper

/**
 * @author  jieyi
 * @since   2017/12/02
 */
class MusicPlayerHelper private constructor() {
    private object Holder {
        val INSTANCE = MusicPlayerHelper()
    }

    private lateinit var player: ExoPlayerWrapper
    private lateinit var musicUri: String

    companion object {
        val instance: MusicPlayerHelper by lazy { Holder.INSTANCE }
    }

    fun hold(player: ExoPlayerWrapper) {
        this.player = player
    }

    fun play(uri: String, callback: ((state: ExoPlayerWrapper.PlayerState) -> Unit)? = null) {
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

    fun getCurrentUri() = if (::musicUri.isInitialized) musicUri else ""

    fun isPlaying() = ExoPlayerWrapper.PlayerState.Play == getState()

    fun isPause() = ExoPlayerWrapper.PlayerState.Pause == getState()

    fun isStop() = ExoPlayerWrapper.PlayerState.Standby == getState()
}