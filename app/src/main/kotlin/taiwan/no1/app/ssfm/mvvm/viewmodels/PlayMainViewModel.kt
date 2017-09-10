package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainViewModel(activity: Activity): BaseViewModel(activity) {
    var music = ObservableField<DetailMusicEntity>()

    init {
        this.music.set(DetailMusicEntity())
    }
}