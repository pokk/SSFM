package taiwan.no1.app.ssfm.features.chart

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.devrapid.dialogbuilder.QuickDialogBindingFragment
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.bottomsheet_track.rl_bottom_sheet
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.databinding.FragmentDialogPlaylistBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel
import taiwan.no1.app.ssfm.features.bottomsheet.quickDialogBindingFragment
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT_NEEDBACK
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_NAME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.NAVIGATION_TO_FRAGMENT
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_ALBUM
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_RANK_CHART
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_SIMILAR
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_DISMISS_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_LONG_CLICK
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class ChartActivity : AdvancedActivity<ChartViewModel, ActivityChartBinding>() {
    @Inject override lateinit var viewModel: ChartViewModel
    @Inject lateinit var addPlaylistItemCase: AddPlaylistItemCase
    @field:[Inject Named("activity_playlist_usecase")] lateinit var fetchPlaylistCase: FetchPlaylistCase
    private val playlistInfo by lazy { DataInfo() }
    private val permissions by lazy { RxPermissions(this) }
    private var playlistRes = mutableListOf<BaseEntity>()
    private lateinit var dialogFragment: QuickDialogBindingFragment<FragmentDialogPlaylistBinding>

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomSheetVm =
            BottomSheetViewModel(this,
                                 permissions,
                                 BottomSheetBehavior.from(rl_bottom_sheet).apply {
                                     state = BottomSheetBehavior.STATE_HIDDEN
                                 } as BottomSheetBehavior<View>,
                                 addPlaylistItemCase)

        navigate(hashMapOf(RXBUS_PARAMETER_FRAGMENT to ChartIndexFragment.newInstance(),
                           RXBUS_PARAMETER_FRAGMENT_NEEDBACK to false))
        RxBus.get().register(this)
    }

    override fun onResume() {
        super.onResume()
        playlistRes.clear()
    }

    override fun onStop() {
        super.onStop()
        dismissPlaylistDialog("")
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
        binding.bottomSheetVm = null
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_chart)
    //endregion

    override fun onBackPressed() {
        if (BottomSheetBehavior.STATE_EXPANDED == BottomSheetBehavior.from(rl_bottom_sheet).state) {
            BottomSheetBehavior.from(rl_bottom_sheet).state = BottomSheetBehavior.STATE_HIDDEN
            return
        }
        super.onBackPressed()
    }

    //region RxBus Response
    /**
     * @param artistName
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.ChartViewModel.querySubmit]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartSimilarArtistViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopArtistViewModel.itemOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_SIMILAR)])
    fun navigateToArtistDetail(artistName: String) {
        navigate(hashMapOf(RXBUS_PARAMETER_FRAGMENT to ChartArtistDetailFragment.newInstance(
            artistName = artistName),
                           RXBUS_PARAMETER_FRAGMENT_NEEDBACK to true))
    }

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopAlbumViewModel.itemOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_ALBUM)])
    fun navigateToAlbumDetail(params: HashMap<String, String>) {
        val (artistName, artistAlbum) =
            (params[VIEWMODEL_PARAMS_ARTIST_NAME].orEmpty()) to (params[VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME].orEmpty())
        navigate(hashMapOf(RXBUS_PARAMETER_FRAGMENT to ChartAlbumDetailFragment.newInstance(
            artistAlbum,
            artistName),
                           RXBUS_PARAMETER_FRAGMENT_NEEDBACK to true))
    }

    /**
     * @param entity
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartRankChartViewModel.chartOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_RANK_CHART)])
    fun navigateToRankChartDetail(entity: RankChartEntity) {
        navigate(hashMapOf(
            RXBUS_PARAMETER_FRAGMENT to ChartRankChartDetailFragment.newInstance(entity.rankType,
                                                                                 entity),
            RXBUS_PARAMETER_FRAGMENT_NEEDBACK to true))
    }

    /**
     * @param fragParams A hash map for storing
     * 1. [Fragment] a fragment
     * 2. [Boolean] a boolean value for return
     *
     * @event_from [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchArtistChartViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartTagViewModel.tagOnClick]
     */
    @Subscribe(tags = [Tag(NAVIGATION_TO_FRAGMENT)])
    fun navigate(fragParams: HashMap<String, Any>) {
        val fragment = fragParams[RXBUS_PARAMETER_FRAGMENT] as Fragment
        val needBack = fragParams[RXBUS_PARAMETER_FRAGMENT_NEEDBACK] as Boolean

        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }

    /**
     * @param entity
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewRankChartDetailViewModel.trackOnLongClick]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopTrackViewModel.trackOptionalOnClick]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartArtistHotTrackViewModel.trackOptionalOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_TRACK_LONG_CLICK)])
    fun openBottomSheet(entity: Any) {
        (entity as BaseEntity).let { binding.bottomSheetVm?.run { obtainMusicEntity = it } }
        BottomSheetBehavior.from(rl_bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
    }

    /**
     * @param entity
     *
     * @event_from [taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel.debounceOpenDialog]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG)])
    fun openPlaylistDialog(entity: Any) {
        playlistRes.clear()
        dialogFragment = this@ChartActivity.quickDialogBindingFragment(entity,
                                                                       playlistRes,
                                                                       playlistInfo,
                                                                       fetchPlaylistCase,
                                                                       addPlaylistItemCase)
    }

    /**
     * @param any
     *
     * @event_from [taiwan.no1.app.ssfm.features.bottomsheet.RecyclerViewDialogPlaylistViewModel.debounceAddToPlaylist]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_DISMISS_PLAYLIST_FRAGMENT_DIALOG)])
    fun dismissPlaylistDialog(any: Any) {
        if (::dialogFragment.isInitialized) {
            dialogFragment.dismiss()
        }
    }
    //endregion
}
