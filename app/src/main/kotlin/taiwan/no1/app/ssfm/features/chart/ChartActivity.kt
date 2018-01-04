package taiwan.no1.app.ssfm.features.chart

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.devrapid.dialogbuilder.QuickDialogBindingFragment
import com.devrapid.kotlinknifer.addFragment
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import kotlinx.android.synthetic.main.bottomsheet_track.rl_bottom_sheet
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.databinding.FragmentDialogPlaylistBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel
import taiwan.no1.app.ssfm.features.bottomsheet.RecyclerViewDialogPlaylistViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_NAME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_ALBUM
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_RANK_CHART
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_SIMILAR
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_DISSMISS_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_LONG_CLICK_RANK_CHART
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DFPlaylistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
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
    private var playlistRes = mutableListOf<BaseEntity>()
    private lateinit var dialogFragment: QuickDialogBindingFragment<FragmentDialogPlaylistBinding>

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomSheetVm = BottomSheetViewModel(BottomSheetBehavior.from(rl_bottom_sheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        } as BottomSheetBehavior<View>)
        navigate(ChartIndexFragment.newInstance(), false)
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

    /**
     * @param artistName
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.ChartViewModel.querySubmit]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartSimilarArtistViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewTagTopArtistViewModel.itemOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_SIMILAR)])
    fun navigateToArtistDetail(artistName: String) {
        navigate(ChartArtistDetailFragment.newInstance(artistName = artistName), true)
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
        navigate(ChartAlbumDetailFragment.newInstance(artistAlbum, artistName), true)
    }

    /**
     * @param entity
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartRankChartViewModel.chartOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_RANK_CHART)])
    fun navigateToRankChartDetail(entity: RankChartEntity) {
        navigate(ChartRankChartDetailFragment.newInstance(entity.rankType, entity), true)
    }

    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }

    /**
     * @param entity
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewRankChartDetailViewModel.trackOnLongClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_LONG_CLICK_RANK_CHART)])
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
        dialogFragment = QuickDialogBindingFragment.Builder<FragmentDialogPlaylistBinding>(this) {
            viewCustom = R.layout.fragment_dialog_playlist
        }.build().apply {
            bind = { binding ->
                binding.vm = ChartDialogViewModel(playlistRes.isEmpty(), fetchPlaylistCase).apply {
                    onAttach(this@ChartActivity)
                    fetchedPlaylistCallback = {
                        playlistRes.refreshAndChangeList(it.subList(1, it.size), 1,
                            binding.adapter as DFPlaylistAdapter, playlistInfo)
                    }
                    binding.layoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL,
                        false)
                    binding.decoration = VerticalItemDecorator(20)
                    binding.adapter = DFPlaylistAdapter(R.layout.item_playlist_type_2,
                        playlistRes) { holder, item, index ->
                        holder.binding.avm =
                            RecyclerViewDialogPlaylistViewModel(item, entity as BaseEntity, addPlaylistItemCase).apply {
                                onAttach(this@ChartActivity)
                            }
                    }
                }
            }
        }.apply { show() }
    }

    /**
     * @param any
     *
     * @event_from [taiwan.no1.app.ssfm.features.bottomsheet.RecyclerViewDialogPlaylistViewModel.debounceAddToPlaylist]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_DISSMISS_PLAYLIST_FRAGMENT_DIALOG)])
    fun dismissPlaylistDialog(any: Any) {
        if (::dialogFragment.isInitialized) {
            dialogFragment.dismiss()
        }
    }
}
