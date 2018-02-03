package taiwan.no1.app.ssfm.features.play

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainActivity : AdvancedActivity<PlayMainViewModel, ActivityMusicBinding>() {
    @Inject override lateinit var viewModel: PlayMainViewModel
    private val path: Array<String> = arrayOf("https://soundsthatmatterblog.files.wordpress.com/2012/12/04-just-give-me-a-reason-feat-nate-ruess.mp3",
                                              "/storage/emulated/0/Download/test.mp3")
    private var albumRes = mutableListOf<BaseEntity>(
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"),
        AlbumEntity.AlbumWithArtist(ArtistEntity.Artist(), "0"))

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rcii_album.apply {
            startTime = playerHelper.currentTime
            endTime = playerHelper.trackDuration

        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_music)
    //endregion
}