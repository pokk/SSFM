package taiwan.no1.app.ssfm.mvvm.models

/**
 * @author jieyi
 * @since 5/22/17
 */

data class DetailMusicModel(
        var status: Int = 0,
        var err_code: Int = 0,
        var data: DataBean? = null) {

    data class DataBean(
            var hash: String? = null,
            var timelength: Int = 0,
            var filesize: Int = 0,
            var audio_name: String? = null,
            var have_album: Int = 0,
            var album_name: String? = null,
            var album_id: Int = 0,
            var img: String? = null,
            var have_mv: Int = 0,
            var video_id: String? = null,
            var author_name: String? = null,
            var song_name: String? = null,
            var lyrics: String? = null,
            var author_id: String? = null,
            var play_url: String? = null,
            var bitrate: Int = 0,
            var authors: List<AuthorsBean>? = null)

    data class AuthorsBean(
            var is_publish: String? = null,
            var author_id: String? = null,
            var avatar: String? = null,
            var author_name: String? = null)
}