package taiwan.no1.app.ssfm.functions.base

import com.devrapid.kotlinknifer.SharedPrefs
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.gContext
import taiwan.no1.app.ssfm.misc.extension.gIntArray
import taiwan.no1.app.ssfm.misc.extension.gStringArray
import taiwan.no1.app.ssfm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.models.data.remote.RemoteDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.AddRankChartUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase

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
            zip(gStringArray(R.array.UpdatePeriod), this::pairToTriple).
            forEach { (type, name, update) ->
                // Get the image cover of each rank charts in the first time.
                GetMusicRankUsecase(RemoteDataStore(gContext())).execute(GetMusicRankUsecase.RequestValue(type)) {
                    onNext {
                        AddRankChartUsecase(LocalDataStore()).
                            execute(AddRankChartUsecase.RequestValue(RankChartEntity(0, type,
                                it.data.songs[0].coverURL, name, update))) { onNext { logd(it) } }
                    }
                }
            }
    }

    private fun <A, B, C> pairToTriple(a: Pair<A, B>, b: C) = Triple(a.first, a.second, b)
}