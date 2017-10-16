package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class ChartTopArtistEntity(var artists: Artists?) {
    data class Artists(var artist: List<Artist?>?,
                       @SerializedName("@attr") var attr: Attr?)

    data class Artist(
        var name: String?,
        var playcount: String?,
        var listeners: String?,
        var mbid: String?,
        var url: String?,
        var streamable: String?,
        var image: List<Image?>?
    )

    data class Image(@SerializedName("#text") var text: String?,
                     var size: String?)

    data class Attr(
        var page: String?,
        var perPage: String?,
        var totalPages: String?,
        var total: String?
    )
}