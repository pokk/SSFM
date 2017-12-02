package taiwan.no1.app.ssfm.misc.utilies.devices

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import taiwan.no1.app.ssfm.misc.utilies.PausableTimer

/**
 * Created by weian on 2017/11/16.
 *
 */
class ExoPlayerWrapper(private val context: Context,
                       private var durationListener: (duration: Int) -> Unit,
                       private var bufferPercentage: (percentage: Int) -> Unit,
                       private var currentTime: (time: Int) -> Unit) {
    private var isPlaying = false
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var timer: PausableTimer

    /**
     * start playing a music. When the music is playing, user is calling again, then music will be paused.
     */
    fun play(url: String) {
        initExoPlayer(url)
        exoPlayer.playWhenReady = true
    }

    fun play() {
        if (isPlaying) {
            timer.pause()
        }
        else {
            timer.resume()
        }
        exoPlayer.playWhenReady = !isPlaying
    }

    /**
     * stop playing the music.
     */
    fun stop() {
        exoPlayer.playWhenReady = false
        exoPlayer.release()
        timer.stop()
    }

    /**
     * pause a music. If no music is played, nothing to do.
     */
    fun pause() {
        exoPlayer.playWhenReady = false
        timer.pause()
    }

    /**
     * resume the playing of the music.
     */
    fun resume() {
        exoPlayer.playWhenReady = true
        timer.resume()
    }

    /**
     * set the repeat mode: normal play, repeat one music, repeat the whole playlist
     */
    fun repeat(isRepeat: Boolean) {
        exoPlayer.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    /**
     * seek the play time when the music is playing
     */
    fun seekTo(time: Int) {
        exoPlayer.seekTo(time.times(1000).toLong())
    }

    fun isPlaying() = isPlaying

    private fun initExoPlayer(url: String) {
        val meter = DefaultBandwidthMeter()
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "LocalExoPlayer"), meter)
        val uri = Uri.parse(url)
        val extractorMediaSource = ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
        val trackSelector = DefaultTrackSelector(meter)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        exoPlayer.addListener(LocalPlayerEventListener(this, exoPlayer))
        exoPlayer.prepare(extractorMediaSource)
    }

    private class LocalPlayerEventListener(private val wrapper: ExoPlayerWrapper,
                                           private val exoPlayer: ExoPlayer) : Player.EventListener {
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            wrapper.isPlaying = playWhenReady
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            wrapper.bufferPercentage(exoPlayer.bufferedPercentage)
        }

        override fun onPositionDiscontinuity() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
            if (exoPlayer.duration > 0) {
                wrapper.durationListener(exoPlayer.duration.div(1000).toInt())
            }

            wrapper.timer = PausableTimer(exoPlayer.duration, 1)
            wrapper.timer.onTick = { millisUntilFinished ->
                wrapper.currentTime(millisUntilFinished.div(1000).toInt())
            }
            wrapper.timer.onFinish = {
                wrapper.currentTime(exoPlayer.duration.div(1000).toInt())
            }
            wrapper.timer.start()
        }
    }
}