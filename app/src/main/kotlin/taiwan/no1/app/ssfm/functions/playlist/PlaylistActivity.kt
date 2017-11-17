package taiwan.no1.app.ssfm.functions.playlist

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity : AdvancedActivity<PlaylistViewModel, ActivityPlaylistBinding>() {
    @Inject override lateinit var viewModel: PlaylistViewModel

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
        fragmentManager.addFragment(R.id.fl_container, PlaylistIndexFragment.newInstance(), false)
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_playlist)
    //endregion

    /**
     * @param playlist
     *
     * @event_from [taiwan.no1.app.ssfm.functions.playlist.RecyclerViewPlaylistViewModel.playlistOnClick]
     * @event_from [taiwan.no1.app.ssfm.functions.playlist.PlaylistViewModel.addPlaylistOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusTag.VIEWMODEL_CLICK_PLAYLIST), Tag(RxBusTag.VIEWMODEL_CLICK_ADDP_LAYLIST)))
    fun navigateToPlaylistDetail(playlist: PlaylistEntity) {
        navigate(PlaylistDetailFragment.newInstance(playlist), true)
    }

    private fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }
}