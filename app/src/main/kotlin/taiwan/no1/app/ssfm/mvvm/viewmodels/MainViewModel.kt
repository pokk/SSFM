package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import de.umass.lastfm.Chart
import io.reactivex.rxkotlin.subscribeBy
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.TestModel
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore
import taiwan.no1.app.ssfm.mvvm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import kotlin.concurrent.thread

/**
 *
 * @author  jieyi
 * @since   5/8/17
 */
class MainViewModel(activity: Activity): BaseViewModel(activity) {
    private val model: TestModel = TestModel("Jieyi", 20)

    var test: ObservableField<TestModel> = ObservableField()

    init {
        this.test.set(this.model)
    }

    fun itemClick(view: View) {
        val user = this.context.getString(R.string.lastfm_name)
        val password = this.context.getString(R.string.lastfm_password)
        val key = this.context.getString(R.string.lastfm_key)
        val secret = this.context.getString(R.string.lastfm_secret_key)

        val remote: IDateStore = RemoteDataStore(this.context)
        val local: IDateStore = LocalDataStore()

        val repo = DataRepository(local, remote)

//        repo.obtainSession(user, password, key, secret).
//                // TODO: 5/12/17 Consider a good way to import this life cycle.
//                //                compose(RxLifecycleAndroid.bindActivity((this.context as MainActivity).lifecycle())).
//                subscribeBy({
//                    logw(it)
//                    // NOTE: 5/12/17 Just one place hold kind of the operation.
//                    this.context.getSharedPreferences("Test", MODE_PRIVATE).edit().putString("session",
//                            Gson().toJson(it)).
//                            apply()
//                }, {
//                    loge(it)
//                }, {
//                    logw()
//                })

        thread {
            //            val chart = Chart.getTopArtists(key)
//            val artists = chart.pageResults
//            artists.forEach { logd(it.getImageURL(ImageSize.LARGE)) }

            val chart = Chart.getTopTracks(key).pageResults.forEach { it }
        }

        repo.getDetailMusicRes("e2a060761620ff482a272b67b204774d").subscribeBy({
            logw(it)
        }, {
            loge(it.message)
            loge(it)
        }, {
            logd()
        })
    }
}
