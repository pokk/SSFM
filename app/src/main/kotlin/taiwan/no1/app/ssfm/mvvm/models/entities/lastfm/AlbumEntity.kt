package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName


/**
 * @author  jieyi
 * @since   10/16/17
 */
data class AlbumEntity(var album: Album?) {
    data class Album(var artist: String?): BaseAlbum()

    data class AlbumWithArtist(var artist: ArtistEntity.Artist?): BaseAlbum()

    open class BaseAlbum(@SerializedName("@attr") var attr: Attr? = null,
                         @SerializedName("image") var images: List<Image>? = null,
                         var listeners: String? = null,
                         var mbid: String? = null,
                         var name: String? = null,
                         @SerializedName("playcount") var playCount: String? = null,
                         var tags: Tags? = null,
                         var title: String? = null,
                         @SerializedName("tracks") var track: TopTrackEntity.Tracks? = null,
                         var url: String? = null,
                         var wiki: Wiki? = null): BaseEntity
}