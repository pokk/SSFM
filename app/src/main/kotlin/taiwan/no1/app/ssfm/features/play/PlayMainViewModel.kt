package taiwan.no1.app.ssfm.features.play

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainViewModel(private val context: Context) : BaseViewModel() {
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_home)) }
    val trackName by lazy { ObservableField<String>("The Cure") }
    val artistName by lazy { ObservableField<String>("Lady Gaga") }

    init {
        playerHelper.currentPlayingTrack.let {
            trackName.set(it?.trackName.orEmpty())
            artistName.set(it?.artistName.orEmpty())
        }
    }

    fun lyricsOnClick(view: View) {
    }

    fun prevOnClick(view: View) = playerHelper.previous()

    fun nextOnClick(view: View) = playerHelper.next()

    fun repeatOnClick(view: View) {
        playerHelper.mode
    }

    fun shuffleOnClick(view: View) {
        playerHelper.mode
    }
}