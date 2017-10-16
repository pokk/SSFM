package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class TrackEntity(var track: Track?) {
    data class Track(
        var name: String?,
        var mbid: String?,
        var url: String?,
        var duration: String?,
        var streamable: Streamable?,
        var listeners: String?,
        var playcount: String?,
        var artist: Artist?,
        var album: Album?,
        var toptags: Toptags?,
        var wiki: Wiki?)

    data class Wiki(var published: String?,
                    var summary: String?,
                    var content: String?)

    data class Album(var artist: String?,
                     var title: String?,
                     var mbid: String?,
                     var url: String?,
                     var image: List<Image?>?,
                     @SerializedName("@attr")
                     var attr: Attr?)

    data class Image(@SerializedName("#text") var text: String?,
                     var size: String?)

    data class Attr(var position: String?)

    data class Toptags(var tag: List<Tag?>?)

    data class Tag(var name: String?,
                   var url: String?)

    data class Streamable(@SerializedName("#text") var text: String?,
                          var fulltrack: String?)

    data class Artist(var name: String?,
                      var mbid: String?,
                      var url: String?)
}