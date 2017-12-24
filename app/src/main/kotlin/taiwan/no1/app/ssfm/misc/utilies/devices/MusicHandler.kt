package taiwan.no1.app.ssfm.misc.utilies.devices

import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   12/24/17
 */
fun LifecycleProvider<*>.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase: SearchMusicV2Case,
                                                                addPlaylistItemCase: AddPlaylistItemCase,
                                                                keyWord: String,
                                                                stateEventListener: (MusicPlayerState) -> Unit = {},
                                                                block: (PlaylistItemEntity) -> Unit = {}) {
    execute(searchMusicCase, SearchMusicUsecase.RequestValue(keyWord)) {
        onNext {
            it.data.items.first().run {
                val playlistEntity = PlaylistItemEntity(trackUri = url,
                    trackName = title,
                    artistName = artist,
                    coverUrl = coverURL,
                    lyricUrl = lyricURL,
                    duration = length)

                playThenToPlaylist(addPlaylistItemCase, playlistEntity, stateEventListener, block)
            }
        }
    }
}

fun LifecycleProvider<*>.playThenToPlaylist(addPlaylistItemCase: AddPlaylistItemCase,
                                            playlistEntity: PlaylistItemEntity,
                                            stateEventListener: (MusicPlayerState) -> Unit = {},
                                            block: (PlaylistItemEntity) -> Unit = {}) {
    block(playlistEntity)
    MusicPlayerHelper.instance.run {
        play(playlistEntity.trackUri) {
            // Add this history to database.
            execute(addPlaylistItemCase,
                AddPlaylistItemUsecase.RequestValue(playlistEntity.apply { playlistId = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong() })) {}
        }
        addStateChangedListeners(stateEventListener)
    }
}