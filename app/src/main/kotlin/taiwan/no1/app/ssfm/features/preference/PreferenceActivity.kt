package taiwan.no1.app.ssfm.features.preference

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.SharedPrefs
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPreferenceBinding
import taiwan.no1.app.ssfm.databinding.ItemPreferenceFirstLayerTitleBinding
import taiwan.no1.app.ssfm.databinding.ItemPreferenceFirstLayerToggleBinding
import taiwan.no1.app.ssfm.databinding.ItemPreferenceSecondLayerTitleBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.extension.recyclerview.MultipleTypeAdapter
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders.BindingHolder
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
class PreferenceActivity : AdvancedActivity<PreferenceViewModel, ActivityPreferenceBinding>() {
    @Inject override lateinit var viewModel: PreferenceViewModel
    private var theme by SharedPrefs(false)
    private var isAutoPlay by SharedPrefs(false)
    private var isAutoDownload by SharedPrefs(false)
    private var isDownloadByWifi by SharedPrefs(false)
    private var isLockScreenLyrics by SharedPrefs(false)
    private var isLastSelectedChannel by SharedPrefs(false)
    private val preferenceList: MutableList<IExpandVisitable> = mutableListOf(
        PreferenceEntity("Theme", "Dark", R.drawable.ic_theme, childItemList = mutableListOf(
            PreferenceOptionEntity("Dark"),
            PreferenceOptionEntity("Light"))),
        PreferenceToggleEntity("Auto Play", isAutoPlay, R.drawable.ic_music_disk),
        PreferenceToggleEntity("Auto Download", isAutoDownload, R.drawable.ic_download),
        PreferenceToggleEntity("Download Only Wifi", isDownloadByWifi, R.drawable.ic_wifi),
        PreferenceToggleEntity("Lock Screen Lyrics Display", isLockScreenLyrics, R.drawable.ic_queue_music),
        PreferenceToggleEntity("Last Selected Channel", isLastSelectedChannel, R.drawable.ic_chart),
        PreferenceEntity("About Us", "", R.drawable.ic_info_outline),
        PreferenceEntity("Feedback", "", R.drawable.ic_feedback))

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initial the recycler view.
        binding.apply {
            layoutManager = WrapContentLinearLayoutManager(this@PreferenceActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MultipleTypeAdapter(this@PreferenceActivity, preferenceList) { holder, item, _ ->
                when (item) {
                    is PreferenceEntity -> {
                        (holder as BindingHolder<ItemPreferenceFirstLayerTitleBinding>).binding.avm =
                            PreferenceItemViewModel(binding.adapter as MultipleTypeAdapter,
                                                    preferenceList.indexOf(item), item)
                    }
                    is PreferenceOptionEntity -> {
                        (holder as BindingHolder<ItemPreferenceSecondLayerTitleBinding>).binding.avm =
                            PreferenceOptionViewModel(item)
                    }
                    is PreferenceToggleEntity -> {
                        (holder as BindingHolder<ItemPreferenceFirstLayerToggleBinding>).binding.avm =
                            PreferenceToggleViewModel(item).apply {
                                setBack = { entityName, checked ->
                                    when (entityName) {
                                        "Auto Play" -> isAutoPlay = checked
                                        "Auto Download" -> isAutoDownload = checked
                                        "Download Only Wifi" -> isDownloadByWifi = checked
                                        "Lock Screen Lyrics Display" -> isLockScreenLyrics = checked
                                        "Last Selected Channel" -> isLastSelectedChannel = checked
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId() = this to R.layout.activity_preference
    //endregion
}