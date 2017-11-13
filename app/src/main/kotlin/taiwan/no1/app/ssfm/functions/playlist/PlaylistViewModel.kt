package taiwan.no1.app.ssfm.functions.playlist

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class PlaylistViewModel(private val context: Context): BaseViewModel() {
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_my_playlist)) }

    fun addPlaylistOnClick(view: View?) {
    }

    fun moreOnClick(view: View?) {
    }
}