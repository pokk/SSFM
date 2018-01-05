package taiwan.no1.app.ssfm.misc.utilies.devices

import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_BUFFER_PRECENT_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_CURRENT_TIME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_DURATION_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_STATE_CHANGED
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

    fun downloadMusic(uri: String) = player.writeToFile(uri)

    private fun setPlayerListener() {
        player.setEventListener(PlayerEventListenerImpl {
            onDurationChanged = { RxBus.get().post(MUSICPLAYER_DURATION_CHANGED, it) }
            onCurrentTime = { RxBus.get().post(MUSICPLAYER_CURRENT_TIME, it) }
            onBufferPercentage = { RxBus.get().post(MUSICPLAYER_BUFFER_PRECENT_CHANGED, it) }
            /**
             * @event_to [taiwan.no1.app.ssfm.features.playlist.RecyclerViewPlaylistDetailViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchMusicResultViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartArtistHotTrackViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartAlbumTrackViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewRankChartDetailViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopTrackViewModel.playerStateChanged]
             */
            onPlayerStateChanged = { RxBus.get().post(MUSICPLAYER_STATE_CHANGED, it) }
        })
    }
}
