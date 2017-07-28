package taiwan.no1.app.ssfm.mvvm.ui.activities

//import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.utilies.devices.MediaPlayerProxy
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
        val mediaplayer = MediaPlayerProxy().apply {
            play("http://fs.web.kugou.com/510fd09bac3950bc0905ea074627d3eb/597b16e5/G007/M06/1B/18/p4YBAFS7O62AMdAgAD1iMghMan8590.mp3")
        }
    }
}