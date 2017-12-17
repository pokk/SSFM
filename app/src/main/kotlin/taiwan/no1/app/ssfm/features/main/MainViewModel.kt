package taiwan.no1.app.ssfm.features.main

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.TagEntity
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase

/**
 *
 * @author  jieyi
 * @since   5/8/17
 */
class MainViewModel(private val context: Context,
                    private val usecase: FetchMusicDetailCase) :
    BaseViewModel() {
    private val entity = TagEntity(20, "Jieyi")

    var test = ObservableField<TagEntity>(entity)

    fun itemClick(view: View) {
    }
}
