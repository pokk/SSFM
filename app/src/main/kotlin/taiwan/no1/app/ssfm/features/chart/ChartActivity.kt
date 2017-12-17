package taiwan.no1.app.ssfm.features.chart

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinknifer.addFragment
import com.devrapid.kotlinknifer.logw
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import kotlinx.android.synthetic.main.bottomsheet_track.rl_bottom_sheet
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.BOTTOMSHEET_DOWNLOAD
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_NAME
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.onBottomSheetClickItem
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import java.util.HashMap
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class ChartActivity : AdvancedActivity<ChartViewModel, ActivityChartBinding>() {
    @Inject override lateinit var viewModel: ChartViewModel
    private var track by WeakRef<BaseEntity>()

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate(ChartIndexFragment.newInstance(), false)
        RxBus.get().register(this)
        BottomSheetBehavior.from(rl_bottom_sheet).state = BottomSheetBehavior.STATE_HIDDEN
        // TODO(jieyi): 2017/12/15 This should be in viewmodel.
        rl_bottom_sheet.onBottomSheetClickItem { _, which ->
            when (which) {
                BOTTOMSHEET_DOWNLOAD -> {
                    logw((track as? MusicRankEntity.Song))
                }
            }
        }
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
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
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewUniversal2ViewModel.itemOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_SIMILAR)])
    fun navigateToArtistDetail(artistName: String) {
        navigate(ChartArtistDetailFragment.newInstance(artistName = artistName), true)
    }

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewUniversal1ViewModel.itemOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_ALBUM)])
    fun navigateToAlbumDetail(params: HashMap<String, String>) {
        val (artistName, artistAlbum) =
            (params[VIEWMODEL_PARAMS_ARTIST_NAME].orEmpty()) to (params[VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME].orEmpty())
        navigate(ChartAlbumDetailFragment.newInstance(artistAlbum, artistName), true)
    }

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.features.chart.RecyclerViewChartRankChartViewModel.chartOnClick]
     */
    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_CLICK_RANK_CHART)])
    fun navigateToRankChartDetail(entity: RankChartEntity) {
        navigate(ChartRankChartDetailFragment.newInstance(entity.rankType, entity), true)
    }

    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }

    fun openBottomSheet(entity: BaseEntity) {
        track = entity
        BottomSheetBehavior.from(rl_bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
    }
}
