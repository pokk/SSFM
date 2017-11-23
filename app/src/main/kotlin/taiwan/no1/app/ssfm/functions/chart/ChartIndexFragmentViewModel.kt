package taiwan.no1.app.ssfm.functions.chart

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTagCase
import taiwan.no1.app.ssfm.models.usecases.GetTopArtistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTopTagsUsecase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartIndexFragmentViewModel(private val topArtistsUsecase: FetchTopArtistCase,
                                  private val topTagsUsecase: FetchTopTagCase) :
    BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsUsecase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists, it.artists.attr?.total?.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TagEntity.Tag>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTagsUsecase, GetTopTagsUsecase.RequestValue(page, limit)) {
            onNext { callback(it.tag.tags, it.tag.attr?.total?.toInt() ?: 0) }
        }
}