package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName


/**
 * @author  jieyi
 * @since   10/16/17
 */

data class AlbumEntity(var album: Album?) {
    data class Album(var name: String?,
                     var artist: String?,
                     var mbid: String?,
                     var url: String?,
                     var image: List<Image?>?,
                     var listeners: String?,
                     var playcount: String?,
                     var tracks: Tracks?,
                     var tags: Tags?,
                     var wiki: Wiki?)

    data class Image(@SerializedName("#text") var text: String?,
                     var size: String?)

    data class Wiki(var published: String?,
                    var summary: String?,
                    var content: String?)

    data class Tags(var tag: List<Tag?>?)

    data class Tag(var name: String?,
                   var url: String?)

    data class Tracks(var track: List<Track?>?)

    data class Track(var name: String?,
                     var url: String?,
                     var duration: String?,
                     @SerializedName("@attr") var attr: Attr?,
                     var streamable: Streamable?,
                     var artist: Artist?)

    data class Attr(var rank: String)

    data class Streamable(@SerializedName("#text") var text: String?,
                          var fulltrack: String?)

    data class Artist(var name: String?,
                      var mbid: String?,
                      var url: String?)
}
