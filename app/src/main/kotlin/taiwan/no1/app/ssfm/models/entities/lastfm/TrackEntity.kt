package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */
data class TrackEntity(var track: Track?) {
    data class Track(var streamable: Streamable?): BaseTrack()

    data class TrackWithStreamableString(var streamable: String?): BaseTrack()

    open class BaseTrack(var album: AlbumEntity.Album? = null,
                         @SerializedName("@attr") var attr: Attr? = null,
                         var artist: ArtistEntity.Artist? = null,
                         var duration: String? = null,
                         @SerializedName("image") var images: List<Image>? = null,
                         var listeners: String? = null,
                         var match: Double? = null,
                         var mbid: String? = null,
                         var name: String? = null,
                         @SerializedName("playcount") var playcount: String? = null,
                         @SerializedName("toptags") var topTag: Tags? = null,
                         var url: String? = null,
                         var wiki: Wiki? = null): BaseEntity
}