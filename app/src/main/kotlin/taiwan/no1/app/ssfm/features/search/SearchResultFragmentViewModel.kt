package taiwan.no1.app.ssfm.features.search

import com.devrapid.kotlinknifer.loge
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.transforms.mToPlaylist
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragmentViewModel(private val searchUsecase: SearchMusicV2Case) : BaseViewModel() {
    fun sendSearchRequest(keyword: String, page: Int = 0,
                          resultCallback: (keyword: String, musics: MutableList<PlaylistItemEntity>, canLoadMore: Boolean) -> Unit) =
        if (keyword.isNotBlank()) {
            lifecycleProvider.execute(searchUsecase, SearchMusicUsecase.RequestValue(keyword, page)) {
                onNext {
                    // Raise the stop loading more data flag.
                    it.data.items.mToPlaylist().subscribe { tracks ->
                        resultCallback(keyword, tracks, it.data.has_more)
                    }
                }
                onError {
                    loge(it.message)
                    loge(it)
                }
            }
        }
        else {
            // This situation is the first click `search view` or keyword is blank.
            resultCallback(keyword, mutableListOf(), false)
        }
}