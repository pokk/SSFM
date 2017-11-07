package taiwan.no1.app.ssfm.functions.playlist

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity: AdvancedActivity<PlaylistViewModel, ActivityPlaylistBinding>() {
    @Inject override lateinit var viewModel: PlaylistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.addFragment(R.id.fl_container, PlaylistIndexFragment.newInstance(), false)
    }

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_playlist)
    //endregion
}