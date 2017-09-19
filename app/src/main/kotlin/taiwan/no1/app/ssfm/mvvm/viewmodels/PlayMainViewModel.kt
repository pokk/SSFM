package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.ObservableString
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainViewModel(activity: Activity): BaseViewModel(activity) {
    var music = ObservableField<DetailMusicEntity>()
    val title = ObservableString()

    init {
        title.set(activity.getString(R.string.menu_home))
        music.set(DetailMusicEntity())
    }
}