package taiwan.no1.app.ssfm.mvvm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 *
 * @author  jieyi
 * @since   10/17/17
 */
data class Image(@SerializedName("#text") var text: String?,
                 var size: String?)

data class Streamable(@SerializedName("#text") var text: String?,
                      var fulltrack: String?)

data class Wiki(var published: String?,
                var summary: String?,
                var content: String?)

data class Attr(var artist: String?,
                var totalPages: String?,
                var total: String?,
                var rank: String?,
                var position: String?,
                var perPage: String?,
                var page: String?)

