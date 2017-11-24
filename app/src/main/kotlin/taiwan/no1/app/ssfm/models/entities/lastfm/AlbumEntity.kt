package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName


/**
 * @author  jieyi
 * @since   10/16/17
 */
data class AlbumEntity(var album: Album?) {
    data class Album(var artist: String?,
                     @SerializedName("playcount")
                     var playCount: String? = null) : BaseAlbum()

    data class AlbumWithArtist(var artist: ArtistEntity.Artist?,
                               @SerializedName("playcount")
                               var playCount: String? = null) : BaseAlbum()

    data class AlbumWithPlaycount(var artist: ArtistEntity.Artist?,
                                  @SerializedName("playcount")
                                  var playCount: Int?) : BaseAlbum()

    open class BaseAlbum(@SerializedName("@attr")
                         var attr: Attr? = null,
                         @SerializedName("image")
                         var images: List<Image>? = null,
                         var listeners: String? = null,
                         var mbid: String? = null,
                         var name: String? = null,
                         var tags: Tags? = null,
                         var title: String? = null,
                         @SerializedName("tracks")
                         var track: TopTrackEntity.Tracks? = null,
                         var url: String? = null,
                         var wiki: Wiki? = null) : BaseEntity
}