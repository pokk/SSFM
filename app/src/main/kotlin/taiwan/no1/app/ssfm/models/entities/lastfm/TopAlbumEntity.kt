package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class TopAlbumEntity(var albums: Topalbums) {
    data class Topalbums(@SerializedName("album") var albums: List<AlbumEntity.AlbumWithArtist>,
                         @SerializedName("@attr") var attr: Attr?)
}