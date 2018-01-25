package taiwan.no1.app.ssfm.features.chart

import android.annotation.SuppressLint
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.transforms.sToPlaylist
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

    @SuppressLint("CheckResult")
    fun fetchRankChartDetail(code: Int,
                             entity: RankChartEntity?,
                             callback: (entity: List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code), {
            doOnNext { it.data.songs.sToPlaylist().subscribe(callback) }
                // Get the first track of the rank track list and obtain its cover uri then keeping
                // the uri to the rank type entity.
                .map {
                    it.data.songs.firstOrNull()?.run {
                        entity?.also {
                            it.coverUrl = coverURL
                            backgroundImageUrl.set(coverURL)
                        }
                    }
                }
                // Update the entity info from the database.
                .flatMap { editRankChartUsecase.pureUsecase(AddRankChartUsecase.RequestValue(it)) }
        }) { onNext {} }
    }
}