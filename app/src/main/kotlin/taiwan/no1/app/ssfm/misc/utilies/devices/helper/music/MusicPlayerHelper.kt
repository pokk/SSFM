package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logi
import com.devrapid.kotlinknifer.logw
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_BUFFER_PRECENT_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_CURRENT_TIME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_DURATION_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_STATE_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.extension.copy
import taiwan.no1.app.ssfm.misc.extension.gContext
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.EPlayerMode.PLAYLIST_STATE_NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.LoopAll
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.LoopOne
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Normal
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Random
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.Unknown
import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistManager
import taiwan.no1.app.ssfm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.models.data.remote.RemoteDataStore
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicUriUsecase
import weian.cheng.mediaplayerwithexoplayer.ExoPlayerEventListener.PlayerEventListener
import weian.cheng.mediaplayerwithexoplayer.ExoPlayerWrapper
import weian.cheng.mediaplayerwithexoplayer.IMusicPlayer
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Pause
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Play
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Standby
import javax.inject.Singleton

/**
 * @author  jieyi
 * @since   2017/12/02
 */
@Singleton
class MusicPlayerHelper private constructor() {
    private object Holder {
        val INSTANCE = MusicPlayerHelper()
    }

    companion object {
        // For singleton object.
        val instance by lazy { Holder.INSTANCE }

        //region A new ExoPlayer for obtaining the information.
        private var lock = false
        private val instanceForInfo by lazy { ExoPlayerWrapper(gContext()) }
        private var durationCallback by WeakRef<(Int) -> Unit>()

        fun obtainDuration(uri: String, callback: (Int) -> Unit) {
            if (Standby != instanceForInfo.getPlayerState()) {
                callback(-1)
                loge("The player doesn't ready!")
                return
            }

            instanceForInfo.play(uri)
            if (!lock) {
                lock = true
                durationCallback = callback
                instanceForInfo.setEventListener(PlayerEventListener {
                    onDurationChanged = durationCallback
                    instanceForInfo.stop()
                    lock = false
                })
            }
            else
                callback(-1)
        }
        //endregion
    }

    /** The player's play mode. See [taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.EPlayerMode] */
    var mode = PLAYLIST_STATE_NORMAL
    /** The object place which is playing the music. */
    var currentObject = "!!!"
    /** Keeping the last time which object was playing the music. */
    var playInObject = "???"
    /** Current playing track's duration. Unit is 'second'. */
    var trackDuration = -1
        private set
    /** Current track's playing time. Unit is 'second'. */
    var currentTime = -1
        private set
    val isFirstTimePlayHere get() = currentObject != playInObject
    /** Player's current state. */
    val state get() = player.getPlayerState()
    /** The quantity of the tracks in the playlist. */
    val playlistSize get() = playlistManager?.playlistSize ?: 0
    /**
     * The current playing track's uri.
     * For solving the problem is that avoiding the play icon and pause icon on the view.
     */
    val currentUri get() = if (::musicUri.isInitialized) musicUri else "None"
    /** The index of the current playing track in the playlist. */
    val currentPlaylistIndex get() = playlistManager?.currentIndex ?: -1
    // FIXME(jieyi): 2/15/18 There're some problems will cause the app crash.
    val currentPlayingTrack get() = playlistManager?.playlist?.get(currentPlaylistIndex)
    /** Check the track finishes playing. */
    val isPlayedTrack get() = 0 == currentTime
    val isPlaying get() = Play == state
    val isPause get() = Pause == state
    val isStop get() = Standby == state
    // FIXME(jieyi): 2018/01/22 ********* We need to get the object from dagger 2. *********
    private val searchUsecase = GetMusicUriUsecase(DataRepository(LocalDataStore(), RemoteDataStore(gContext())))
    private var playlistManager: PlaylistManager<PlaylistItemEntity>? = null
    private lateinit var player: IMusicPlayer
    private lateinit var musicUri: String

    /**
     * Register the [player] object and the [playlistManager] object.
     *
     * @param player the media player object.
     * @param playlistManager managing the play list object.
     */
    fun hold(player: IMusicPlayer, playlistManager: PlaylistManager<PlaylistItemEntity>? = null) {
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

    /**
     * Play a track from the [uri].
     *
     * @param uri track uri. If you don't input this parameter, just continue to play the same track.
     * @param callback the player state callback function.
     */
    fun play(uri: String = musicUri, callback: stateChangedListener = null) {
        if (::musicUri.isInitialized && isCurrentUri(uri)) {
            // It will 
            // 1. continue to play the same track.
            // 2. pause the player.
            // 3. repeat the same track.
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
            // It will change a new track and notify to the view models that what's the new track.
            try {
                if (::player.isInitialized && isPlaying) player.stop()
                player.play(uri)
                callback?.invoke(state)
                musicUri = uri
            }
            catch (e: Exception) {
                logw("catch", musicUri)
                loge("Now is playing the music...Just wait a moment!")
            }
            finally {
                RxBus.get().post(VIEWMODEL_TRACK_CLICK, musicUri)
            }
        }
    }

    fun pause() = player.pause()

    fun stop() = player.stop()

    /**
     * Directly play the next track from the playlist.
     *
     * @param callback the player state callback function.
     */
    fun next(callback: stateChangedListener = null) =
        mode.playerMode.next?.let { entity -> play(entity.trackUri, callback) } ?: throw Exception()

    /**
     * Directly play the previous track from the playlist.
     *
     * @param callback the player state callback function.
     */
    fun previous(callback: stateChangedListener = null) =
        mode.playerMode.previous?.let { entity -> play(entity.trackUri, callback) } ?: throw Exception()

    fun downloadMusic(uri: String) = player.writeToFile(uri)

    /**
     * Add a new track [list] into the playlist.
     *
     * @param list a track list of the track entities.
     */
    fun addList(list: List<PlaylistItemEntity>) = playlistManager?.append(list) ?: false

    /**
     * Remove a track entity from the playlist.
     *
     * @param uri a track entity.
     */
    fun removeTrack(uri: PlaylistItemEntity) = playlistManager?.remove(uri) ?: false

    /**
     * Remove a track entity from the playlist by the index.
     *
     * @param index the track index in the playlist.
     */
    fun removeTrack(index: Int) = playlistManager?.remove(index)

    /**
     * Remove all tracks from the playlist.
     */
    fun clearList() = playlistManager?.clearPlaylist() ?: Unit

    fun setCurrentIndex(uri: PlaylistItemEntity) = playlistManager?.setIndex(uri) ?: false

    /**
     * Add the [PlaylistItemEntity] list to the [playlistManager].
     *
     * @param playlistItem the current playing track's uri.
     * @param newSource a list of the [PlaylistItemEntity].
     * @param place the 'object name' where calls this function.
     */
    fun addToPlaylist(playlistItem: PlaylistItemEntity, newSource: List<PlaylistItemEntity>, place: String) {
        playerHelper.also { helper ->
            if (helper.isFirstTimePlayHere) {
                helper.clearList()
                helper.playInObject = place
            }
            helper.addList(newSource)
            helper.setCurrentIndex(playlistItem)
        }
    }

    /**
     * Fetch the real track uri from the api and attach to the [PlaylistItemEntity].
     *
     * @param playlistItem a list of the [PlaylistItemEntity].
     */
    fun attachMusicUri(playlistItem: List<PlaylistItemEntity>) =
        playlistItem.copy().apply {
            // For fetching the missing music uri's items.
            forEach {
                // Avoiding that there is a music uri, and fetching it again.
                if (it.trackUri.isNotBlank()) return@forEach
                playerHelper.fetchMusicUri(it)
            }
        }

    /**
     * Get the music uri for the item without the music uri. Also, the item *variables* will be modified.
     *
     * @param playlistItem
     */
    private fun fetchMusicUri(playlistItem: PlaylistItemEntity) {
        if (playlistItem.trackUri.isNotBlank()) return

        return searchUsecase.execute(GetMusicUriUsecase.RequestValue(playlistItem),
                                     null,
                                     {
                                         // Just get the most exact track. NOTE: It might be incorrect, but the most exact!
                                         map { it.data.items.first() }
                                     }) {
            onNext {
                logi(playlistItem)
                // Attach the track uri to the entity.
                playlistItem.apply {
                    trackUri = it.url
                    lyricUrl = it.lyricURL
                    duration = trackDuration
                }
                logi(playlistItem)
            }
        }
    }

    /**
     * Set the default player state changing listener.
     */
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

    /**
     * When a track was finished playing, it'll fetch the next track uri and play it automatically.
     */
    private fun autoPlayNext() =
        if (state == Standby && isPlayedTrack) {
            mode.playerMode.next?.let {
                play(it.trackUri)
                RxBus.get().post(VIEWMODEL_TRACK_CLICK, it.trackUri)
            }
        }
        else
            Unit
}
