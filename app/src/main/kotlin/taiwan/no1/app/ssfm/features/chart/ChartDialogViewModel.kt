package taiwan.no1.app.ssfm.features.chart

import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartDialogViewModel(fetchPlaylistCase: FetchPlaylistCase) : BaseViewModel() {
    var fetchedPlaylistCallback: (List<PlaylistEntity>) -> Unit = {}

    init {
//        lifecycleProvider.execute(fetchPlaylistCase) {
//            onNext(fetchedPlaylistCallback)
//        }
    }
}