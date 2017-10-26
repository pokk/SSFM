package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */
data class TrackSimilarEntity(@SerializedName("similartracks") var similartrack: TopTrackEntity.Tracks)