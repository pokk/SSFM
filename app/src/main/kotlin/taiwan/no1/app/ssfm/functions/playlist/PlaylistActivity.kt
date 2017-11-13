package taiwan.no1.app.ssfm.functions.playlist

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity : AdvancedActivity<PlaylistViewModel, ActivityPlaylistBinding>() {
    @Inject override lateinit var viewModel: PlaylistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.addFragment(R.id.fl_container, PlaylistIndexFragment.newInstance(), false)
    }

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this,
        R.layout.activity_playlist)
    //endregion

    /**
     * @param artistName
     *
     * @event_from [taiwan.no1.app.ssfm.functions.playlist.RecyclerViewPlaylistViewModel.playlistOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_SIMILAR)))
    fun navigateToArtistDetail(playlistId: Int) {
        navigate(PlaylistDetailFragment.newInstance(), true)
    }

    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }
}