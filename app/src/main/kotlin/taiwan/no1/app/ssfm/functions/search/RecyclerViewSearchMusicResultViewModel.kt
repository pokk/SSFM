package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private val res: BaseEntity,
                                             private val addPlaylistItemCase: AddPlaylistItemCase,
                                             private val context: Context) : BaseViewModel() {
    val songName by lazy { ObservableField<String>() }
    val singerName by lazy { ObservableField<String>() }
    val coverUrl by lazy { ObservableField<String>() }
    val showBackground by lazy { ObservableField<Boolean>() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { _, _, _, _, _ ->
            showBackground.set(true)
            false
        }
    }

    init {
        (res as MusicEntity.Music).let {
            songName.set(it.title)
            singerName.set(it.artist)
            coverUrl.set(it.coverURL)
        }
    }

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        (res as MusicEntity.Music).run {
            MusicPlayerHelper.instance.play(url) {
                lifecycleProvider.execute(addPlaylistItemCase,
                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                        trackUri = url,
                        trackName = title,
                        artistName = artist,
                        coverUrl = coverURL,
                        lyricUrl = lyricURL,
                        duration = length))) { onNext { logw(it) } }
            }
        }
    }

    fun optionClick(view: View) {
    }
    //endregion
}