package taiwan.no1.app.ssfm.mvvm.views.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_preference.rv_preference
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.views.BaseActivity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter

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

        val itemList: MutableList<IExpandVisitable> = mutableListOf(Company("Google", listOf<Department>(), true),
            Company("Facebook", mutableListOf(
                Department("CEO", mutableListOf<Department>(), false),
                Department("CTO", mutableListOf<Department>(), false),
                Department("CDO", mutableListOf<Department>(), false),
                Department("CAO", mutableListOf<Department>(), false),
                Department("COO", mutableListOf<Department>(), false)
            ), true),
            Company("Apple", mutableListOf(
                Department("AEO", mutableListOf<Department>(), false),
                Department("ATO", mutableListOf<Department>(), false),
                Department("ADO", mutableListOf<Department>(), false)
            ), true),
            Company("Airbnb", mutableListOf<Department>(), false),
            Company("Jieyi", mutableListOf<Department>(), false))

        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = ExpandRecyclerViewAdapter(itemList)
    }

    override fun onResume() {
        super.onResume()
    }
}