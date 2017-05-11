package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.devrapid.kotlinknifer.AppLog
import de.umass.lastfm.Artist
import de.umass.lastfm.Authenticator
import de.umass.lastfm.Caller
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMainBinding
import taiwan.no1.app.ssfm.internal.di.HasComponent
import taiwan.no1.app.ssfm.internal.di.components.FragmentComponent
import taiwan.no1.app.ssfm.mvvm.ui.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel


class MainActivity: AdvancedActivity<MainViewModel, ActivityMainBinding>(), HasComponent<FragmentComponent> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
//        binding.setVariable(BR.viewmodel, MainViewModel(this.applicationContext))
        binding.viewmodel = MainViewModel(this)
    }

    override fun onResume() {
        super.onResume()

        // NOTE: 5/11/17 We can use the cache as like this way.
//        Caller.getInstance().cache = FileSystemCache(File("${Environment.getExternalStorageDirectory()}/.lastfm"))

        Caller.getInstance().cache = null
        Caller.getInstance().userAgent = "tst"

        val user = this.getString(R.string.lastfm_name)
        val password = this.getString(R.string.lastfm_password)
        val key = this.getString(R.string.lastfm_key)
        val secret = this.getString(R.string.lastfm_secret_key)

        Observable.just("").subscribeOn(Schedulers.computation()).map {
            val session = Authenticator.getMobileSession(user, password, key, secret)
            AppLog.w(session)
            val top = Artist.getTopAlbums("ladygaga", session.apiKey)
            AppLog.d(top)
        }.subscribe {
            AppLog.i(Thread.currentThread())
        }

        val o = Observable.create(ObservableOnSubscribe<String> { it.onNext("string") }).subscribe { AppLog.w(it) }
    }

    override fun bind() {
        this.binding.viewmodel = this.viewModel
    }

    override fun unbind() {
        this.binding.viewmodel = null
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_main)

    override fun provideViewModel(): MainViewModel = MainViewModel(this)

    override fun getFragmentComponent(): FragmentComponent = super.provideFragmentComponent()
}
