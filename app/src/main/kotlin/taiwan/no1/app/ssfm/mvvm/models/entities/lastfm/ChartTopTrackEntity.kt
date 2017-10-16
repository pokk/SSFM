package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class ChartTopTrackEntity(var tracks: Tracks) {
    data class Tracks(var track: List<Track?>?,
                      @SerializedName("@attr") var attr: Attr?)

    data class Track(var name: String?,
                     var duration: String?,
                     var playcount: String?,
                     var listeners: String?,
                     var mbid: String?,
                     var url: String?,
                     var streamable: Streamable?,
                     var artist: Artist?,
                     var image: List<Image?>?)

    data class Attr(var page: String?,
                    var perPage: String?,
                    var totalPages: String?,
                    var total: String?)
}