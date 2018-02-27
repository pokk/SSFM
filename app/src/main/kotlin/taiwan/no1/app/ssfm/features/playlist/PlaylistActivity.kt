package taiwan.no1.app.ssfm.features.playlist

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.view.View
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.bottomsheet_track.*
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlaylistBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.OPEN_SERVICE
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity : AdvancedActivity<PlaylistViewModel, ActivityPlaylistBinding>() {
    @Inject override lateinit var viewModel: PlaylistViewModel
    @Inject lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val permissions by lazy { RxPermissions(this) }

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomSheetVm = BottomSheetViewModel(this,
                                                     permissions,
                                                     BottomSheetBehavior.from(rl_bottom_sheet).apply {
                                                         state = BottomSheetBehavior.STATE_HIDDEN
                                                     } as BottomSheetBehavior<View>,
                                                     addPlaylistItemCase)
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
    override fun provideBindingLayoutId(): Pair<Activity, Int> =
        Pair(this, R.layout.activity_playlist)
    //endregion

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.features.playlist.RecyclerViewPlaylistViewModel.debouncePlaylistClick]
     * @event_from [taiwan.no1.app.ssfm.features.playlist.PlaylistViewModel.addPlaylistOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_PLAYLIST), Tag(RxBusTag.VIEWMODEL_CLICK_ADD_PLAYLIST)])
    fun navigateToPlaylistDetail(params: Pair<PlaylistEntity, List<Pair<View, String>>>) {
        val sharedElements =
            params.second.takeIf { it.isNotEmpty() }?.let { HashMap(it.toMap()) } ?: HashMap()
        navigate(PlaylistDetailFragment.newInstance(params.first,
                                                    sharedElements.map { it.value }.toList()),
                 true,
                 sharedElements)
    }

    @Subscribe(tags = [Tag(OPEN_SERVICE)])
    fun openService(any: String) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        ActivityCompat.startActivityForResult(this,
                                              Intent.createChooser(intent, "Select Picture"),
                                              32,
                                              null)
    }

    private fun navigate(
        fragment: Fragment,
        needBack: Boolean,
        sharedElements: HashMap<View, String>
    ) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack, null, sharedElements)
    }
}