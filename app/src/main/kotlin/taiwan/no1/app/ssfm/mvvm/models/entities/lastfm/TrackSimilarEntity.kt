package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class TrackSimilarEntity(var similartracks: Similartracks?) {
    data class Similartracks(var track: List<Track?>?,
                             @SerializedName("@attr") var attr: Attr?)

    data class Track(var name: String?,
                     var playcount: Int?,
                     var mbid: String?,
                     var match: Double?,
                     var url: String?,
                     var streamable: Streamable?,
                     var duration: Int?,
                     var artist: Artist?,
                     var image: List<Image?>?)
}