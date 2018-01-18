package taiwan.no1.app.ssfm.models.entities.transforms

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.misc.constants.ImageSizes
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity

/**
 * @author  jieyi
 * @since   1/19/18
 */
fun MusicEntity.Music.transformToPlaylist() =
    PlaylistItemEntity(trackUri = url,
                       trackName = title,
                       artistName = artist,
                       coverUrl = coverURL,
                       lyricUrl = lyricURL,
                       duration = length)

fun List<MusicEntity.Music>.mToPlaylist() =
    transform(MusicEntity.Music::transformToPlaylist)

fun MusicRankEntity.Song.transformToPlaylist() =
    PlaylistItemEntity(trackUri = url,
                       trackName = title,
                       artistName = artist,
                       coverUrl = coverURL,
                       lyricUrl = lyricURL,
                       duration = length)

fun List<MusicRankEntity.Song>.sToPlaylist() =
    transform(MusicRankEntity.Song::transformToPlaylist)

fun TrackEntity.BaseTrack.transformToPlaylist() =
    PlaylistItemEntity(trackName = name.orEmpty(),
                       artistName = artist?.name.orEmpty(),
                       albumName = album?.name.orEmpty(),
                       coverUrl = images?.get(ImageSizes.LARGE)?.text.orEmpty(),
                       duration = duration?.toInt() ?: 0)

fun List<TrackEntity.BaseTrack>.tToPlaylist() =
    transform(TrackEntity.BaseTrack::transformToPlaylist)

private fun <T : Any> List<T>.transform(transformToPlaylist: (T) -> PlaylistItemEntity) =
    toFlowable().map(transformToPlaylist).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())