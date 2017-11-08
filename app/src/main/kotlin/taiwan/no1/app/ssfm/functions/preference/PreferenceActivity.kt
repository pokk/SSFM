package taiwan.no1.app.ssfm.functions.preference

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.part_main_preference.rv_preference
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPreferenceBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.models.IExpandVisitable
import taiwan.no1.app.ssfm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.models.entities.PreferenceOptionEntity
import taiwan.no1.app.ssfm.models.entities.PreferenceToggleEntity
import javax.inject.Inject

/**
 * An activity for preference setting.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceActivity: AdvancedActivity<PreferenceViewModel, ActivityPreferenceBinding>() {
    @Inject override lateinit var viewModel: PreferenceViewModel

    private val preferenceList: MutableList<IExpandVisitable> = mutableListOf(
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

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initial the recycler view.
        rv_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_preference.adapter = ExpandRecyclerViewAdapter(preferenceList)
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_preference)
    //endregion
}