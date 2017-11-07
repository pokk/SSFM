package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */
data class TopArtistEntity(var artists: Artists) {
    data class Artists(@SerializedName("artist") var artists: List<ArtistEntity.Artist>,
                       @SerializedName("@attr") var attr: Attr?)
}