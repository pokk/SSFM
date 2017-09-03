package taiwan.no1.app.ssfm.mvvm.views.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_preference.rv_preference
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.BaseActivity
import taiwan.no1.app.ssfm.mvvm.views.recyclerview.BaseAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerview.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerview.Department

/**
 * Just for testing the custom view.
 *
 * @author  jieyi
 * @since   6/8/17
 */
class TestActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val itemList = listOf(Company("Google", listOf<Department>(), false),
            Company("Facebook", listOf<Department>(), false),
            Company("Apple", listOf<Department>(), false),
            Company("Airbnb", listOf<Department>(), false),
            Company("Jieyi", listOf<Department>(), false))

        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = object: BaseAdapter(itemList) {}
    }

    override fun onResume() {
        super.onResume()
    }
}