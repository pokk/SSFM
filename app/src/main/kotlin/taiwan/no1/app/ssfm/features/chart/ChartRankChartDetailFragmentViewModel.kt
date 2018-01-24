package taiwan.no1.app.ssfm.features.chart

import android.annotation.SuppressLint
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
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
//        lifecycleProvider
//            .compose(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code))
//            .doOnNext { it.data.songs.sToPlaylist().subscribe(callback) }
//            .map { it.data.songs.first() }
//            .map { song ->
//                entity?.apply {
//                    coverUrl = song.coverURL
//                    backgroundImageUrl.set(song.coverURL)
//                } ?: RankChartEntity()
//            }
//            .flatMap { editRankChartUsecase.execute(AddRankChartUsecase.RequestValue(it)) }
//            .subscribe {}
        lifecycleProvider
            .execute(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code), {
                map {
                    it.data.songs.firstOrNull()?.also {
                        coverUrl = it.coverURL
                        backgroundImageUrl.set(it.coverURL)
                    } ?: RankChartEntity()
                }
            }) {
                onNext {

                }
            }

    }
}