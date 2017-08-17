package taiwan.no1.app.ssfm.mvvm.models.entities

import android.arch.persistence.room.*
import taiwan.no1.app.ssfm.mvvm.models.data.local.room.converter.IntConverter
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
    var data: DataBean? = null)

@Entity(tableName = "music_table")
@TypeConverters(IntConverter::class)
data class DataBean constructor(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @Ignore var hash: String?,
    @Ignore var timelength: Int,
    @Ignore var filesize: Int,
    @Ignore var audio_name: String?,
    @Ignore var have_album: Int,
    var album_name: String?,
    @Ignore var album_id: Int,
    @Ignore var img: String?,
    @Ignore var have_mv: Int,
    @Ignore var video_id: String?,
    @Ignore var author_name: String?,
    var song_name: String?,
    @Ignore var lyrics: String?,
    @ColumnInfo(name = "singer_name") var author_id: String?,
    @Ignore var play_url: String?,
    @Ignore var bitrate: Int = 0,
    @Ignore var authors: List<AuthorsBean>?,
    var last_play_time: Date,
    @ColumnInfo(name = "offline") var is_offline: Boolean,
    @ColumnInfo(name = "tag") var tag: List<Int>?)

data class AuthorsBean(
    var is_publish: String? = null,
    var author_id: String? = null,
    var avatar: String? = null,
    var author_name: String? = null)