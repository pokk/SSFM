package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.PlaylistViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity: AdvancedActivity<PlaylistViewModel, ActivityPlaylistBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this activity using the provided parameters.
         *
         * @return A new instance of [Activity] PlaylistActivity.
         */
        fun newInstance() = PlaylistActivity()
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistViewModel

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_playlist)
    //endregion
}