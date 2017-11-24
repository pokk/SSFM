package taiwan.no1.app.ssfm.functions.base

import com.devrapid.kotlinknifer.SharedPrefs
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import io.reactivex.functions.Consumer
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.gIntArray
import taiwan.no1.app.ssfm.misc.extension.gStringArray
import taiwan.no1.app.ssfm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase

/**
 * @author  jieyi
 * @since   11/24/17
 */
class FirstInitFlow {
    private var isFirstStartApp by SharedPrefs(false)

    fun init() {
        // OPTIMIZE(jieyi): 11/17/17 The first active activity must rendered history data.
        if (!isFirstStartApp) {
            addHistoryData()
            addRankChartData()
        }
    }

    private fun addHistoryData() {
        AddPlaylistUsecase(LocalDataStore()).
            execute(AddPlaylistUsecase.RequestValue(PlaylistEntity(name = "history", is_history = true))) {
                onNext { logd("Create the history playlist!") }
                onError { loge("Fail to create the history playlist :(") }
                onComplete { isFirstStartApp = true }
            }
    }

    private fun addRankChartData() {
        gIntArray(R.array.TypeCode).
            zip(gStringArray(R.array.TypeCodeName)).
            zip(gStringArray(R.array.UpdatePeriod)) { a, b -> pairToTriple(a, b) }.
            forEach {
                RankChartEntity(rankType = it.first, chartName = it.second, updateTime = it.third).save().
                    subscribe(Consumer { logw(it) })
            }
    }

    private fun <A, B, C> pairToTriple(a: Pair<A, B>, b: C) = Triple(a.first, a.second, b)
}