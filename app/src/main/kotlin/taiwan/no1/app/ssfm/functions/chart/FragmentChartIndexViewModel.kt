package taiwan.no1.app.ssfm.mvvm.viewmodels

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTagsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartIndexViewModel(private val topArtistsUsecase: BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue>,
                                  private val topTagsUsecase: BaseUsecase<TopTagEntity, GetTopTagsCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsCase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists, it.artists.attr?.total?.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TagEntity.Tag>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTagsUsecase, GetTopTagsCase.RequestValue(page, limit)) {
            onNext { callback(it.tag.tags, it.tag.attr?.total?.toInt() ?: 0) }
        }
}