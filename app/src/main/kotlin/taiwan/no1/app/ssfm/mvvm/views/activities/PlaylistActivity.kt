package layout

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.BaseActivity

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class PlaylistActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
    }
}