package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName


/**
 * @author  jieyi
 * @since   10/16/17
 */
data class AlbumEntity(var album: Album?) {
    data class Album(@SerializedName("@attr") var attr: Attr?,
                     var artist: String?,
                     @SerializedName("image") var images: List<Image>?,
                     var listeners: String?,
                     var mbid: String?,
                     var name: String?,
                     @SerializedName("playcount") var playCount: String?,
                     var tags: Tags?,
                     var title: String?,
                     @SerializedName("tracks") var track: ChartTopTrackEntity.Tracks?,
                     var url: String?,
                     var wiki: Wiki?)
}
