package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */
data class ArtistSimilarEntity(@SerializedName("similarartists")
                               var similarartist: TopArtistEntity.Artists)