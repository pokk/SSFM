package taiwan.no1.app.ssfm.functions.play

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainViewModel(private val context: Context) : BaseViewModel() {
    var music = ObservableField<DetailMusicEntity>(DetailMusicEntity())
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_home)) }

    fun prevClick(view: View) {

    }

    fun nextClick(view: View) {

    }
}