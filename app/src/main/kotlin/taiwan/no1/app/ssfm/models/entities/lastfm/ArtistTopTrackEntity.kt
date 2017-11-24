package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class ArtistTopTrackEntity(var toptracks: Tracks) {
    data class Tracks(@SerializedName("track")
                      var tracks: List<TrackEntity.TrackWithStreamableString>,
                      @SerializedName("@attr")
                      var attr: Attr?)
}
