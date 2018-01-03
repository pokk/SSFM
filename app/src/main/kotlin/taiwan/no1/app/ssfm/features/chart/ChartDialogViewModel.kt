package taiwan.no1.app.ssfm.features.chart

import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartDialogViewModel(private val doFetch: Boolean,
                           private val fetchPlaylistCase: FetchPlaylistCase) : BaseViewModel() {
    var fetchedPlaylistCallback: (MutableList<PlaylistEntity>) -> Unit = {}

    override fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>) {
        super.onAttach(lifecycleProvider)

        if (doFetch) {
            lifecycleProvider.execute(fetchPlaylistCase) { onNext { fetchedPlaylistCallback(it.toMutableList()) } }
        }
    }
}