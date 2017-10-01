package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewMusicResultViewModel(val res: InfoBean, context: Context): BaseObservable() {
    val songName by lazy { ObservableField<String>(res.songname ?: "") }
    val singerName by lazy { ObservableField<String>(res.singername ?: "") }
    val duration by lazy { ObservableField<String>(res.duration.toTimeString()) }

    //region Action from View
    fun playOrStopMusicClick(view: View) {
    }

    fun optionClick(view: View) {
    }
    //endregion
}