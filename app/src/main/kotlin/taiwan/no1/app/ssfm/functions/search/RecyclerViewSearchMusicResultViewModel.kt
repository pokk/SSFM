package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayer
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private val res: BaseEntity,
                                             private val context: Context,
                                             private val getDetailMusicCase: FetchMusicDetailCase) : BaseObservable() {
    val songName by lazy { ObservableField<String>() }
    val singerName by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val coverUrl by lazy { ObservableField<String>() }

    init {
        (res as MusicEntity.Music).let {
            songName.set(it.title)
            singerName.set(it.artist)
            duration.set(0.toTimeString())
            coverUrl.set(it.coverURL)
        }
    }

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        res as MusicEntity.Music

        MusicPlayer.instance.apply {
            if (isPlaying()) stop()
            play(res.url)
        }
    }

    fun optionClick(view: View) {
    }
    //endregion
}