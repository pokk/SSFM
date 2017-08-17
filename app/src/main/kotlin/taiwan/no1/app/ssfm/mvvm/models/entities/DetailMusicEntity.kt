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
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var hash: String? = null,
    var timelength: Int = 0,
    var filesize: Int = 0,
    var audio_name: String? = null,
    var have_album: Int = 0,
    @ColumnInfo(name = "album_name")
    var album_name: String?,
    var album_id: Int = 0,
    var img: String? = null,
    var have_mv: Int = 0,
    var video_id: String? = null,
    var author_name: String? = null,
    @ColumnInfo(name = "song_name")
    var song_name: String?,
    var lyrics: String? = null,
    @ColumnInfo(name = "singer_name")
    var author_id: String?,
    var play_url: String? = null,
    var bitrate: Int = 0,
    @Ignore
    var authors: List<AuthorsBean>? = null,
    @ColumnInfo(name = "last_play_time")
    var last_play_time: Date,
    @ColumnInfo(name = "offline")
    var is_offline: Boolean,
    @ColumnInfo(name = "tag")
    var tag: List<Int>?)

data class AuthorsBean(
    var is_publish: String? = null,
    var author_id: String? = null,
    var avatar: String? = null,
    var author_name: String? = null)