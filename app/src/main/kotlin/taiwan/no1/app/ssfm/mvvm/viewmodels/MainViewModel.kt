package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.AppLog
import com.google.gson.Gson
import io.reactivex.rxkotlin.subscribeBy
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.TestModel
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore
import taiwan.no1.app.ssfm.mvvm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository

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
        AppLog.d(view)
        this.model.name = "ggg"

        val user = this.context.getString(R.string.lastfm_name)
        val password = this.context.getString(R.string.lastfm_password)
        val key = this.context.getString(R.string.lastfm_key)
        val secret = this.context.getString(R.string.lastfm_secret_key)

        val remote: IDateStore = RemoteDataStore()
        val local: IDateStore = LocalDataStore()

        val repo = DataRepository(local, remote)

        repo.obtainSession(user, password, key, secret).
                // TODO: 5/12/17 Consider a good way to import this life cycle.
                //                compose(RxLifecycleAndroid.bindActivity((this.context as MainActivity).lifecycle())).
                subscribeBy({
                    AppLog.w(it)
                    // NOTE: 5/12/17 Just one place hold kind of the operation.
                    this.context.getSharedPreferences("Test", MODE_PRIVATE).edit().putString("session",
                            Gson().toJson(it)).
                            apply()
                }, {
                    AppLog::e
                }, {
                    AppLog::w
                })
    }
}
