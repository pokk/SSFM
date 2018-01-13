package taiwan.no1.app.ssfm.misc.utilies.devices

import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_BUFFER_PRECENT_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_CURRENT_TIME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_DURATION_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_STATE_CHANGED
import taiwan.no1.app.ssfm.misc.utilies.devices.annotations.PlaylistState
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant.PLAYLIST_STATE_NORMAL
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

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    @PlaylistState var mode = PLAYLIST_STATE_NORMAL
    val state get() = player.getPlayerState()
    val currentUri get() = if (::musicUri.isInitialized) musicUri else "None"
    val isPlaying get() = Play == state
    val isPause get() = Pause == state
    val isStop get() = Standby == state
    private var playlistManager: PlaylistManager<String>? = null
    private lateinit var player: IMusicPlayer
    private lateinit var musicUri: String

    fun hold(player: IMusicPlayer, playlistManager: PlaylistManager<String>? = null) {
        this.player = player
        this.playlistManager = playlistManager
        setPlayerListener()
    }

    fun play(uri: String, callback: ((state: MusicPlayerState) -> Unit)? = null) {
        // TODO(jieyi): 1/13/18 Change to state pattern!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (::musicUri.isInitialized && uri == musicUri) {
            when {
                isPlaying -> player.pause()
                isPause -> player.resume()
                isStop -> {
                    player.seekTo(0)
                    callback?.invoke(state)
                }
            }
        }
        else {
            if (::player.isInitialized && isPlaying) player.stop()
            player.play(uri)
            callback?.invoke(state)
            musicUri = uri
        }
    }

    fun next() {

    }

    fun previous() {

    }

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
             * @event_to [taiwan.no1.app.ssfm.features.playlist.RecyclerViewRecentlyPlaylistViewModel.playerStateChanged]
             */
            onPlayerStateChanged = { RxBus.get().post(MUSICPLAYER_STATE_CHANGED, it) }
        })
    }
}
