package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import com.devrapid.kotlinknifer.loge
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_BUFFER_PRECENT_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_CURRENT_TIME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_DURATION_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_STATE_CHANGED
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.EPlayerMode.PLAYLIST_STATE_NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.LoopAll
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.LoopOne
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Normal
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Random
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Unknown
import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistManager
import weian.cheng.mediaplayerwithexoplayer.ExoPlayerEventListener.PlayerEventListener
import weian.cheng.mediaplayerwithexoplayer.IMusicPlayer
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

    var mode = PLAYLIST_STATE_NORMAL
    var currentObject = "!!!"
    var playInObject = "???"
    var trackDuration = -1
        private set
    var currentTime = -1
        private set
    val isFirstTimePlayHere get() = currentObject != playInObject
    val state get() = player.getPlayerState()
    val playlistSize get() = playlistManager?.playlistSize ?: 0
    val currentUri get() = if (::musicUri.isInitialized) musicUri else "None"
    val isPlayedTrack get() = currentTime == trackDuration
    val currentPlaylistIndex get() = playlistManager?.currentIndex ?: -1
    val isPlaying get() = Play == state
    val isPause get() = Pause == state
    val isStop get() = Standby == state
    private var playlistManager: PlaylistManager<String>? = null
    private lateinit var player: IMusicPlayer
    private lateinit var musicUri: String

    fun hold(player: IMusicPlayer, playlistManager: PlaylistManager<String>? = null) {
        this.player = player
        this.playlistManager = playlistManager
        this.playlistManager.takeIf { null != it }?.let {
            LoopAll.instance.hold(it)
            LoopOne.instance.hold(it)
            Normal.instance.hold(it)
            Random.instance.hold(it)
            Unknown.instance.hold(it)
        }
        setPlayerListener()
    }

    fun isCurrentUri(uri: String) = uri == currentUri

    fun isFirstTimePlayHere(objectName: String) = objectName != playInObject

    fun play(uri: String, callback: stateChangedListener = null) {
        if (::musicUri.isInitialized && isCurrentUri(uri)) {
            when {
                isPlaying -> player.pause()
                isPause -> player.resume()
                isStop -> {
                    try {
                        player.seekTo(0)
                    }
                    catch (e: Exception) {
                        loge("Library is wrong = =")
                    }
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

    fun pause() = player.pause()

    fun stop() = player.stop()

    fun next(callback: stateChangedListener = null) =
        mode.playerMode.next?.let { trackUri -> play(trackUri, callback) } ?: throw Exception()

    fun previous(callback: stateChangedListener = null) =
        mode.playerMode.previous?.let { trackUri -> play(trackUri, callback) } ?: throw Exception()

    fun downloadMusic(uri: String) = player.writeToFile(uri)

    fun addList(list: List<String>) = playlistManager?.append(list) ?: false

    fun removeTrack(uri: String) = playlistManager?.remove(uri) ?: false

    fun removeTrack(index: Int) = playlistManager?.remove(index).orEmpty()

    fun clearList() = playlistManager?.clearPlaylist() ?: Unit

    fun setCurrentIndex(uri: String) = playlistManager?.setIndex(uri) ?: false

    private fun setPlayerListener() {
        player.setEventListener(PlayerEventListener {
            onDurationChanged = {
                trackDuration = it
                RxBus.get().post(MUSICPLAYER_DURATION_CHANGED, it)
            }
            onCurrentTime = {
                currentTime = it
                RxBus.get().post(MUSICPLAYER_CURRENT_TIME, it)
            }
            onBufferPercentage = {
                RxBus.get().post(MUSICPLAYER_BUFFER_PRECENT_CHANGED, it)
            }
            /**
             * @event_to [taiwan.no1.app.ssfm.features.playlist.RecyclerViewPlaylistDetailViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchMusicResultViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartArtistHotTrackViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartAlbumTrackViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewRankChartDetailViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopTrackViewModel.playerStateChanged]
             * @event_to [taiwan.no1.app.ssfm.features.playlist.RecyclerViewRecentlyPlaylistViewModel.playerStateChanged]
             */
            onPlayerStateChanged = {
                autoPlayNext()
                RxBus.get().post(MUSICPLAYER_STATE_CHANGED, it)
            }
        })
    }

    private fun autoPlayNext() = if (state == Standby && isPlayedTrack) mode.playerMode.next?.let { play(it) } else Unit
}
