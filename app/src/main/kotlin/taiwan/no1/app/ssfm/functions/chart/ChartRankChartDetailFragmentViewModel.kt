package taiwan.no1.app.ssfm.functions.chart

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase

/**
 * @author  jieyi
 * @since   11/24/17
 */
class ChartRankChartDetailFragmentViewModel(private val getMusicRankUsecase: BaseUsecase<MusicRankEntity, GetMusicRankUsecase.RequestValue>) :
    BaseViewModel() {
    fun fetchRankChartDetail(code: Int, callback: (entity: List<MusicRankEntity.Song>) -> Unit) {
        lifecycleProvider.execute(getMusicRankUsecase, GetMusicRankUsecase.RequestValue(code)) {
            onNext { callback(it.data.songs) }
        }
    }
}