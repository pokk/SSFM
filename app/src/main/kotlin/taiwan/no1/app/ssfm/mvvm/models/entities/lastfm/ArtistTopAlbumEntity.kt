package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class ArtistTopAlbumEntity(var topalbums: Topalbums) {
    data class Topalbums(var album: List<Album>?,
                         var attr: Attr?)

    data class Album(var artist: ArtistEntity.Artist?,
                     @SerializedName("image") var images: List<Image>?,
                     var mbid: String?,
                     var name: String?,
                     var url: String?,
                     @SerializedName("playcount") var playCount: String?)
}