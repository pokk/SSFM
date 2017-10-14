package taiwan.no1.app.ssfm.mvvm.models.entities

import de.umass.lastfm.Track

/**
 * @author  jieyi
 * @since   10/14/17
 */
data class ExtTrackEntity(val track: Track,
                          var imageUrl: String = "")