package taiwan.no1.app.ssfm.models.entities.v2

/**
 * @author  jieyi
 * @since   11/20/17
 */

data class SongListEntity(val status: String = "",
                          val data: Data = Data()) {
    data class Data(val share_uri: String = "",
                    val song_list_id: String = "",
                    val permission: Int = 0,
                    val has_fav: Boolean = false,
                    val user: User = User(),
                    val song_list_type: Int = 0,
                    val song_list_name: String = "",
                    val tags: List<Any> = listOf(),
                    val tag_ids: List<Any> = listOf(),
                    val played_count: String = "",
                    val song_list_cover: String = "",
                    val comment_count: Int = 0,
                    val song_list_desc: String = "",
                    val fav_count: Int = 0,
                    val song_num: Int = 0,
                    val share_count: Int = 0,
                    val is_cover_modified: Boolean = false,
                    val share_link: String = "",
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

    data class Source(val unknown: Any? = null)

    data class Mv(val region_allowed: String = "",
                  val dislikes: Int = 0,
                  val rate: Int = 0,
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
                  val review_info: String = "",
                  val is_public: Int = 0,
                  val y_video_id: String = "",
                  val fm_mv_active: Int = 0,
                  val ctime: String = "",
                  val published_at: String = "",
                  val region_blocked: String = "",
                  val cover_image: String = "")

    data class User(val uid: String = "",
                    val avatar_url: String = "",
                    val gender: Int = 0,
                    val phone: String = "",
                    val birthday: Int = 0,
                    val platform: Int = 0,
                    val address: String = "",
                    val email: String = "",
                    val platform_uid: String = "",
                    val screen_name: String = "")
}
