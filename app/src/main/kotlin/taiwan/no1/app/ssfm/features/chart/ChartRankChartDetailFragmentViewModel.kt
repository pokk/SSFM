package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import com.devrapid.kotlinknifer.logd
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddRankChartUsecase
import taiwan.no1.app.ssfm.models.usecases.EditRankChartCase
import taiwan.no1.app.ssfm.models.usecases.FetchMusicRankCase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase

/**
 * @author  jieyi
 * @since   11/24/17
 */
class ChartRankChartDetailFragmentViewModel(private val getMusicRankUsecase: FetchMusicRankCase,
                                            private val editRankChartUsecase: EditRankChartCase) : BaseViewModel() {
    val backgroundImageUrl by lazy { ObservableField<String>() }

    fun fetchRankChartDetail(code: Int,
                             entity: RankChartEntity?,
                             callback: (entity: List<MusicRankEntity.Song>) -> Unit) {
        lifecycleProvider.execute(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code)) {
            onNext { res ->
                entity?.let {
                    it.coverUrl = res.data.songs[0].coverURL
                    backgroundImageUrl.set(res.data.songs[0].coverURL)
                    lifecycleProvider.execute(editRankChartUsecase, AddRankChartUsecase.RequestValue(it)) {
                        onNext { logd(it) }
                    }
                }
                callback(res.data.songs)
            }
        }
    }
}