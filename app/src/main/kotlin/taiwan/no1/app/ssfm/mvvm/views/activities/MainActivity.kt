package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMainBinding
import taiwan.no1.app.ssfm.misc.extension.addFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.views.fragments.MainFragment
import javax.inject.Inject

class MainActivity: AdvancedActivity<MainViewModel, ActivityMainBinding>() {
    @Inject override lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.setVariable(viewmodel, MainViewModel(this.applicationContext))
        fragmentManager.addFragment(R.id.rl_fragment, MainFragment())
    }

    override fun onResume() {
        super.onResume()


        // NOTE: 5/11/17 We can use the cache as like this way.
//        Caller.getInstance().cache = FileSystemCache(File("${Environment.getExternalStorageDirectory()}/.lastfm"))
//        Caller.getInstance().cache = null
//        Caller.getInstance().userAgent = "tst"

        val user = this.getString(R.string.lastfm_name)
        val password = this.getString(R.string.lastfm_password)
        val key = this.getString(R.string.lastfm_api_key)
        val secret = this.getString(R.string.lastfm_secret_key)

//        val repo = DataRepository(LocalDataStore(), RemoteDataStore(this.applicationContext))
//
//        repo.getDetailMusicRes("e2a060761620ff482a272b67b204774d").
//            subscribeBy({
//                            logw(it)
//                        }, {
//                            loge(it.message)
//                            loge(it)
//                        }, {
//                            logd()
//                        })
//        repo.obtainSession(user, password).subscribe {
//            logd(it)
//            logw(Track.unlove("cher", "believe", it))
//            Radio.tune(Radio.RadioStation("test"), it)
//        }
    }

    override fun bind() {
        this.binding.viewmodel = this.viewModel
    }

    override fun unbind() {
        this.binding.viewmodel = null
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_main)

//    override fun getFragmentComponent(): FragmentComponent = super.provideFragmentComponent()
}
