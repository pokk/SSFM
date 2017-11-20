package taiwan.no1.app.ssfm.models.entities.v2

/**
 * @author  jieyi
 * @since   11/20/17
 */

data class MusicEntity(val status: String = "",
                       val data: Data = Data()) {
    data class Data(val has_more: Boolean = false,
                    val items: List<Item> = listOf())

    data class Item(val share_uri: String = "",
                    val lyricURL: String = "",
                    val title: String = "",
                    val url: String = "",
                    val coverURL: String = "",
                    val song_id_ext: String = "",
                    val artist: String = "",
                    val copyright_type: Int = 0,
                    val flag: Int = 0,
                    val source: Source = Source(),
                    val length: Int = 0,
                    val ori_coverURL: String = "",
                    val uploader: String = "",
                    val sid: Int = 0,
                    val cdn_coverURL: String = "",
                    val other_sources: List<Any> = listOf())

    data class Source(val unknown: Any? = null)
}
