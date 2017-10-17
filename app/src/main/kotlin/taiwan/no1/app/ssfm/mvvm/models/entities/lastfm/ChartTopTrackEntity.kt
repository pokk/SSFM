package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */

data class ChartTopTrackEntity(@SerializedName("tracks") var track: Tracks) {
    data class Tracks(@SerializedName("track") var tracks: List<TrackEntity.Track>?,
                      @SerializedName("@attr") var attr: Attr?)
}