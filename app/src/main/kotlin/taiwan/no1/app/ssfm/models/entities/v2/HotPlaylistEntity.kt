package taiwan.no1.app.ssfm.models.entities.v2

/**
 * @author  jieyi
 * @since   11/20/17
 */

data class HotPlaylistEntity(val status: String = "",
                             val data: Data = Data()) {
    data class Data(val has_more: Int = 0,
                    val song_lists: List<Songs> = listOf())

    data class Songs(val song_list_id: String = "",
                     val song_list_name: String = "",
                     val permission: Int = 0,
                     val tag_ids: List<Int> = listOf(),
                     val played_count: Int = 0,
                     val song_list_cover: String = "",
                     val song_list_desc: String = "",
                     val user: User = User(),
                     val song_num: Int = 0,
                     val song_list_type: Int = 0,
                     val share_count: Int = 0,
                     val fav_count: Int = 0,
                     val is_cover_modified: Boolean = false)

    data class User(val uid: String = "",
                    val platform: Int = 0,
                    val gender: Int = 0,
                    val avatar_url: String = "",
                    val phone: String = "",
                    val birthday: Int = 0,
                    val address: String = "",
                    val email: String = "",
                    val platform_uid: String = "",
                    val screen_name: String = "")
}
