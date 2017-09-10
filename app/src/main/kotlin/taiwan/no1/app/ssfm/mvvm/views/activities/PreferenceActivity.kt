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
            mutableListOf(
                PreferenceEntity("Theme", "Dark", R.drawable.ic_theme, childItemList = mutableListOf(
                    PreferenceOptionEntity("Dark"),
                    PreferenceOptionEntity("Light"))),
                PreferenceToggleEntity("Auto Play", true, R.drawable.ic_music_disk),
                PreferenceToggleEntity("Auto Download", false, R.drawable.ic_download),
                PreferenceToggleEntity("Download Only Wifi", false, R.drawable.ic_wifi),
                PreferenceToggleEntity("Lock Screen Lyrics Display", true, R.drawable.ic_queue_music),
                PreferenceToggleEntity("Last Selected Channel", false),
                PreferenceEntity("About Us", "", R.drawable.ic_info_outline),
                PreferenceEntity("Feedback", "", R.drawable.ic_feedback))

        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = ExpandRecyclerViewAdapter(preferenceList)
    }
}