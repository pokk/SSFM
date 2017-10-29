package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class ArtistTopTrackEntity(var toptracks: Tracks) {
    data class Tracks(@SerializedName("track") var tracks: List<Track>,
                      @SerializedName("@attr") var attr: Attr?)

    data class Track(var name: String?,
                     var playcount: String?,
                     var listeners: String?,
                     var mbid: String?,
                     var url: String?,
                     var streamable: String?,
                     var artist: ArtistEntity.Artist?,
                     var image: List<Image?>?,
                     @SerializedName("@attr") var attr: Attr?): BaseEntity
}
