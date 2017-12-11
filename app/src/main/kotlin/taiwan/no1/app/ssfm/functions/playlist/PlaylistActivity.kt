package taiwan.no1.app.ssfm.functions.playlist

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.View
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
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.functions.playlist.RecyclerViewPlaylistViewModel.playlistOnClick]
     * @event_from [taiwan.no1.app.ssfm.functions.playlist.PlaylistViewModel.addPlaylistOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_PLAYLIST), Tag(RxBusTag.VIEWMODEL_CLICK_ADD_LAYLIST)])
    fun navigateToPlaylistDetail(params: Pair<PlaylistEntity, List<Pair<View, String>>>) {
        val sharedElements = params.second.takeIf { it.isNotEmpty() }?.let { HashMap(it.toMap()) } ?: HashMap()
        navigate(PlaylistDetailFragment.newInstance(params.first,
            sharedElements.map { it.value }.toList()),
            true,
            sharedElements)
    }

    private fun navigate(fragment: Fragment, needBack: Boolean, sharedElements: HashMap<View, String>) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack, sharedElements)
    }
}