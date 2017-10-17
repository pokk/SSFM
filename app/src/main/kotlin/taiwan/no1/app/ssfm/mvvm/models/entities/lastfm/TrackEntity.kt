package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class TrackEntity(var track: Track?) {
    data class Track(var album: AlbumEntity.Album?,
                     @SerializedName("@attr") var attr: Attr?,
                     var artist: ArtistEntity.Artist?,
                     var duration: String?,
                     @SerializedName("image") var images: List<Image>?,
                     var listeners: String?,
                     var match: Double?,
                     var mbid: String?,
                     var name: String?,
                     @SerializedName("playcount") var playcount: String?,
                     var streamable: Streamable?,
                     @SerializedName("toptags") var topTag: Toptags?,
                     var url: String?,
                     var wiki: Wiki?)

    data class Toptags(@SerializedName("tag") var tags: List<Tag?>?)

    data class Tag(var name: String?,
                   var url: String?)
}