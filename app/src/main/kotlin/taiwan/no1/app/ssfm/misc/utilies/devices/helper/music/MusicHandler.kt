package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import com.hwangjr.rxbus.RxBus
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.transforms.transformToPlaylist
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase
import java.util.Date

/**
 * @author  jieyi
 * @since   12/24/17
 */
fun LifecycleProvider<*>.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase: SearchMusicV2Case,
                                                                addPlaylistItemCase: AddPlaylistItemCase,
                                                                keyWord: String,
                                                                playlistId: Long = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                                                                block: (PlaylistItemEntity) -> Unit = {}) {
    execute(searchMusicCase, SearchMusicUsecase.RequestValue(keyWord)) {
        onNext {
            it.data.items
                .first()
                .run { playThenToPlaylist(addPlaylistItemCase, transformToPlaylist(), playlistId, block) }
        }
    }
}

fun LifecycleProvider<*>.playThenToPlaylist(addPlaylistItemCase: AddPlaylistItemCase,
                                            playlistEntity: PlaylistItemEntity,
                                            playlistId: Long = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                                            block: (PlaylistItemEntity) -> Unit = {}) {
    block(playlistEntity)
    playerHelper.apply {
        play(playlistEntity.trackUri) {
            // Add this history to database.
            execute(addPlaylistItemCase,
                    AddPlaylistItemUsecase.RequestValue(playlistEntity.also {
                        it.playlistId = playlistId
                        it.timestamp = Date()
                    })) {}
        }
    }
}

fun LifecycleProvider<*>.playMusic(addPlaylistItemCase: AddPlaylistItemCase,
                                   playlistItem: PlaylistItemEntity,
                                   index: Int) {
    RxBus.get().post(RxBusTag.VIEWMODEL_TRACK_CLICK, index)
    playThenToPlaylist(addPlaylistItemCase, playlistItem) {
        RxBus.get().post(RxBusTag.VIEWMODEL_TRACK_CLICK, it.trackUri)
    }
    RxBus.get().post(RxBusTag.HELPER_ADD_TO_PLAYLIST, playlistItem)
}