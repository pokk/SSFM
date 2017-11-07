package taiwan.no1.app.ssfm.mvvm.models.entities

import com.google.gson.annotations.SerializedName

/**
 * The data structure of searched a music song.
 *
 * @author jieyi
 * @since 5/20/17
 */

data class SearchMusicEntity(var status: Int = 0,
                             var error: String? = null,
                             var data: DataBean? = null,
                             var errcode: Int = 0) {

    data class DataBean(var tab: String? = null,
                        var timestamp: Int = 0,
                        var total: Int = 0,
                        var aggregation: List<AggregationBean>? = null,
                        var info: List<InfoBean>? = null)

    data class AggregationBean(var key: String? = null,
                               var count: Int = 0)

    data class InfoBean(var m4afilesize: Int = 0,
                        var filesize: Int = 0,
                        var source: String? = null,
                        var bitrate: Int = 0,
                        var topic: String? = null,
                        var accompany: Int = 0,
                        var singername: String? = null,
                        var sourceid: Int = 0,
                        var topic_url: String? = null,
                        var filename: String? = null,
                        var extname: String? = null,
                        @SerializedName("320hash") var `_$320hash`: String? = null,
                        var mvhash: String? = null,
                        var album_id: String? = null,
                        var ownercount: Int = 0,
                        var rp_publish: Int = 0,
                        var rp_type: String? = null,
                        var audio_id: Int = 0,
                        @SerializedName("320filesize") var `_$320filesize`: Int = 0,
                        var isnew: Int = 0,
                        var duration: Int = 0,
                        var srctype: Int = 0,
                        var songname: String? = null,
                        var sqhash: String? = null,
                        var album_name: String? = null,
                        var hash: String? = null,
                        var sqfilesize: Int = 0,
                        var othername: String? = null,
                        var group: List<*>? = null)
}