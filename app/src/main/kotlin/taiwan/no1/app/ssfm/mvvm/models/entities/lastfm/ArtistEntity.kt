package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class ArtistEntity(var artist: Artist?) {
    data class Artist(var name: String?,
                      var mbid: String?,
                      var url: String?,
                      var image: List<Image?>?,
                      var streamable: String?,
                      var ontour: String?,
                      var stats: Stats?,
                      var similar: Similar?,
                      var tags: Tags?,
                      var bio: Bio?)

    data class Image(@SerializedName("#text") var text: String?,
                     var size: String?)

    data class Bio(var links: Links?,
                   var published: String?,
                   var summary: String?,
                   var content: String?)

    data class Links(var link: Link?)

    data class Link(@SerializedName("#text") var text: String?,
                    var rel: String?,
                    var href: String?)

    data class Stats(var listeners: String?,
                     var playcount: String?)

    data class Similar(var artist: List<Artist?>?)

    data class Tags(var tag: List<Tag?>?)

    data class Tag(var name: String?,
                   var url: String?)
}
