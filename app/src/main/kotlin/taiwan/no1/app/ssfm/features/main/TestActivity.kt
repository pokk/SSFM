package taiwan.no1.app.ssfm.features.main

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseActivity

/**
 * Just for testing the custom view.
 *
 * @author  jieyi
 * @since   6/8/17
 */
class TestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.part_main_preference)
    }
}