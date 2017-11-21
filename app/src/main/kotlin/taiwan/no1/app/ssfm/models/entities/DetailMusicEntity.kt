package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.models.data.local.database.ListIntConverter
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import java.sql.Date

/**
 * The detail information of a music song.
 *
 * @author jieyi
 * @since 5/22/17
 */
@Deprecated("There is a better api for searching a music, please check v2")
data class DetailMusicEntity(var status: Int = 0,
                             var err_code: Int = 0,
                             var data: DataBean? = null) {

    @Table(database = MusicDatabase::class, name = "MusicEntity")
    data class DataBean(@PrimaryKey(autoincrement = true) var id: Long = 0,
                        @Column var uri: String = "",
                        var hash: String = "",
                        @Column(name = "duration") var timelength: Int = 0,
                        var filesize: Int = 0,
                        var audio_name: String = "",
                        var have_album: Int = 0,
                        @Column var album_name: String = "",
                        var album_id: Int = 0,
                        var img: String = "",
                        var have_mv: Int = 0,
                        var video_id: String = "",
                        @Column(name = "singer_name") var author_name: String = "",
                        @Column var song_name: String = "",
                        var lyrics: String = "",
                        var author_id: String = "",
                        var play_url: String = "",
                        var bitrate: Int = 0,
                        var authors: List<AuthorsBean> = emptyList(),
                        @Column var last_play_time: Date = Date(0),
        // TODO(jieyi): 8/19/17 DBFlow might change to MutableList data type. Instead of MutableList, We use IntArray temporally.
                        @Column(typeConverter = ListIntConverter::class) var tag: IntArray = intArrayOf()) :
        BaseRXModel()

    data class AuthorsBean(var is_publish: String = "",
                           var author_id: String = "",
                           var avatar: String = "",
                           var author_name: String = "")
}