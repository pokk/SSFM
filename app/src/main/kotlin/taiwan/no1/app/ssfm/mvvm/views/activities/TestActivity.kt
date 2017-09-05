package taiwan.no1.app.ssfm.mvvm.views.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_preference.rv_preference
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.IVisitable
import taiwan.no1.app.ssfm.mvvm.views.BaseActivity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.BaseAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department

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

        val itemList: MutableList<IVisitable> = mutableListOf(Company("Google", listOf<Department>(), true),
            Company("Facebook", mutableListOf(
                Department("CEO", mutableListOf<Department>(), true),
                Department("CTO", mutableListOf<Department>(), true),
                Department("CDO", mutableListOf<Department>(), true),
                Department("CAO", mutableListOf<Department>(), true),
                Department("COO", mutableListOf<Department>(), true)
            ), true),
            Company("Apple", mutableListOf(
                Department("AEO", mutableListOf<Department>(), true),
                Department("ATO", mutableListOf<Department>(), true),
                Department("ADO", mutableListOf<Department>(), true)
            ), true),
            Company("Airbnb", mutableListOf<Department>(), false),
            Company("Jieyi", mutableListOf<Department>(), false))

        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = object: BaseAdapter(itemList) {}
    }

    override fun onResume() {
        super.onResume()
    }
}