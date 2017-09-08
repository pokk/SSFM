package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_preference.rv_preference
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceOptionEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceToggleEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter

/**
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        // HACK(jieyi): 9/8/17 Temporally create here.
        val preferenceList: MutableList<IExpandVisitable> =
            mutableListOf(PreferenceToggleEntity("Auto Play", true),
                PreferenceToggleEntity("Auto Download", false),
                PreferenceToggleEntity("Lock Screen Lyrics Display", true),
                PreferenceToggleEntity("Last Selected Channel", false),
                PreferenceEntity("Theme", "Dark", childItemList = mutableListOf(
                    PreferenceOptionEntity("Dark"),
                    PreferenceOptionEntity("Light")))
            )

        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = ExpandRecyclerViewAdapter(preferenceList)
    }
}