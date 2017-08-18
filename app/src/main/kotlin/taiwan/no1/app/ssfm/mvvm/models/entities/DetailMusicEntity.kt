package taiwan.no1.app.ssfm.mvvm.models.entities

import java.util.*

/**
 * The detail information of a music song.
 *
 * @author jieyi
 * @since 5/22/17
 */

data class DetailMusicEntity(
    var status: Int = 0,
    var err_code: Int = 0,
    var data: DataBean? = null) {

    data class DataBean constructor(
        var id: Long,
        var hash: String?,
        var timelength: Int,
        var filesize: Int,
        var audio_name: String?,
        var have_album: Int,
        var album_name: String?,
        var album_id: Int,
        var img: String?,
        var have_mv: Int,
        var video_id: String?,
        var author_name: String?,
        var song_name: String?,
        var lyrics: String?,
        var author_id: String?,
        var play_url: String?,
        var bitrate: Int = 0,
        var authors: List<AuthorsBean>?,
        var last_play_time: Date,
        var is_offline: Boolean,
        var tag: List<Int>?)

    data class AuthorsBean(
        var is_publish: String? = null,
        var author_id: String? = null,
        var avatar: String? = null,
        var author_name: String? = null)
}