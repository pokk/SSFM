package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import de.umass.lastfm.Chart
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.observer
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.TagEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicCase.RequestValue
import kotlin.concurrent.thread

/**
 *
 * @author  jieyi
 * @since   5/8/17
 */
class MainViewModel(activity: Activity, val usecase: BaseUsecase<DetailMusicEntity, RequestValue>):
    BaseViewModel(activity) {
    private val entity = TagEntity(20, "Jieyi")

    var test = ObservableField<TagEntity>()

    init {
        this.test.set(this.entity)
    }

    fun itemClick(view: View) {
        val user = this.context.getString(R.string.lastfm_name)
        val password = this.context.getString(R.string.lastfm_password)
        val key = this.context.getString(R.string.lastfm_api_key)
        val secret = this.context.getString(R.string.lastfm_secret_key)
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

        this.usecase.parameters = DetailMusicCase.RequestValue("e2a060761620ff482a272b67b204774d")
        this.usecase.execute(observer<DetailMusicEntity>().onNext {
            logw(it)
        }.onComplete {
            logd()
        }.onError {
            loge(it)
        })
    }
}
