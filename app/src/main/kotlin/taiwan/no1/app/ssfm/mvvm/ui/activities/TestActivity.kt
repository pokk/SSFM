package taiwan.no1.app.ssfm.mvvm.ui.activities

//import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.ui.BaseActivity


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
        
//        btn.rotatedCircleImageView.imageResource = R.drawable.sample_jieyi_icon
    }
}