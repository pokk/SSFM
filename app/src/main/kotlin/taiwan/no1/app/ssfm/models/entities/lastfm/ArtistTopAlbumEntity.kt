package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class ArtistTopAlbumEntity(var topalbums: TopAlbums) {
    data class TopAlbums(@SerializedName("album")
                         var albums: List<AlbumEntity.AlbumWithPlaycount>,
                         @SerializedName("@attr")
                         var attr: Attr?)
}