package taiwan.no1.app.ssfm.mvvm.views.activities

//import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.sample_button.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.BaseActivity


/**
 *
 * @author  jieyi
 * @since   6/8/17
 */
class TestActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_button)
    }

    override fun onResume() {
        super.onResume()

//        ttt.onClick { logw("let's go!!!") }
        btn.onClick { }
        btns.onClick { }
//
//        ttt.onProgressChanged = {
//            logw(it)
//        }
    }
}