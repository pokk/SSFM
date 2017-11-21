package taiwan.no1.app.ssfm.models.entities.v2

import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   11/20/17
 */

data class MusicRankEntity(val status: String = "",
                           val data: Data = Data()) : BaseEntity {
    data class Data(val timestamp: Double = 0.0,
                    val songs: List<Song> = listOf())

    data class Song(val share_uri: String = "",
                    val lyricURL: String = "",
                    val ori_coverURL: String = "",
                    val title: String = "",
                    val url: String = "",
                    val coverURL: String = "",
                    val song_id_ext: String = "",
                    val artist: String = "",
                    val copyright_type: Int = 0,
                    val flag: Int = 0,
                    val source: Source = Source(),
                    val length: Int = 0,
                    val mv: Mv = Mv(),
                    val uploader: String = "",
                    val sid: Int = 0,
                    val cdn_coverURL: String = "",
                    val other_sources: List<Any> = listOf())

    data class Mv(val region_allowed: String = "",
                  val dislikes: Int = 0,
                  val published_at: String = "",
                  val likes: Int = 0,
                  val mtime: String = "",
                  val duration: String = "",
                  val id: Int = 0,
                  val title: String = "",
                  val views: Int = 0,
                  val language_id: Int = 0,
                  val comments: Int = 0,
                  val source: Int = 0,
                  val description: String = "",
                  val embeddable: Int = 0,
                  val is_active: Int = 0,
                  val cover_image: String = "",
                  val is_public: Int = 0,
                  val y_video_id: String = "",
                  val fm_mv_active: Int = 0,
                  val ctime: String = "",
                  val rate: Int = 0,
                  val region_blocked: String = "",
                  val review_info: String = "")

    data class Source(val unknown: Any? = null)
}
