package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMainBinding
import taiwan.no1.app.ssfm.mvvm.ui.BaseActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel


class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
//        binding.setVariable(BR.viewmodel, MainViewModel(this.applicationContext))
        binding.viewmodel = MainViewModel(this.applicationContext)
    }
}
