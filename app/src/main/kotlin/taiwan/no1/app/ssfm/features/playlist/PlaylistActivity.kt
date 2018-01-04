package taiwan.no1.app.ssfm.features.playlist

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import kotlinx.android.synthetic.main.bottomsheet_track.rl_bottom_sheet
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel
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
        binding.bottomSheetVm = BottomSheetViewModel(BottomSheetBehavior.from(rl_bottom_sheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        } as BottomSheetBehavior<View>)
        RxBus.get().register(this)
        fragmentManager.addFragment(R.id.fl_container, PlaylistIndexFragment.newInstance(), false)
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
        binding.bottomSheetVm = null
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_playlist)
    //endregion

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.features.playlist.RecyclerViewPlaylistViewModel.debouncePlaylistClick]
     * @event_from [taiwan.no1.app.ssfm.features.playlist.PlaylistViewModel.addPlaylistOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_PLAYLIST), Tag(RxBusTag.VIEWMODEL_CLICK_ADD_PLAYLIST)])
    fun navigateToPlaylistDetail(params: Pair<PlaylistEntity, List<Pair<View, String>>>) {
        val sharedElements = params.second.takeIf { it.isNotEmpty() }?.let { HashMap(it.toMap()) } ?: HashMap()
        navigate(PlaylistDetailFragment.newInstance(params.first, sharedElements.map { it.value }.toList()),
                 true,
                 sharedElements)
    }

    private fun navigate(fragment: Fragment, needBack: Boolean, sharedElements: HashMap<View, String>) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack, sharedElements)
    }
}