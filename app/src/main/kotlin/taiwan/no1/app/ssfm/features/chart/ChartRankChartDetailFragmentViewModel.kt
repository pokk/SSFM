package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.compose
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
        val a = lifecycleProvider.
            compose(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code)).
            doOnNext { callback(it.data.songs) }.
            map { it.data.songs.first() }.
            map { song ->
                entity?.apply {
                    coverUrl = song.coverURL
                    backgroundImageUrl.set(song.coverURL)
                } ?: RankChartEntity()
            }.
            flatMap { editRankChartUsecase.execute(AddRankChartUsecase.RequestValue(it)) }.
            subscribe {}
    }
}