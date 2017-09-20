package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class MusicResultViewModel(val res: InfoBean, context: Context): BaseObservable() {
    val songName = ObservableField<String>(res.songname ?: "")
    val singerName = ObservableField<String>(res.singername ?: "")
    val duration = ObservableInt(res.duration)
}