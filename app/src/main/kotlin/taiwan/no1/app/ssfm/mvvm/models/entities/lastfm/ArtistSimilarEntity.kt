package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class ArtistSimilarEntity(var similarartists: Similarartists?) {
    data class Similarartists(var artist: List<Artist?>?,
                              @SerializedName("@attr") var attr: Attr?)

    data class Artist(var name: String?,
                      var mbid: String?,
                      var match: String?,
                      var url: String?,
                      var image: List<Image?>?,
                      var streamable: String?)
}