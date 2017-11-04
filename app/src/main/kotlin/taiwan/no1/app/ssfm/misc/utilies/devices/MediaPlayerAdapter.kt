package taiwan.no1.app.ssfm.misc.utilies.devices

import android.annotation.TargetApi
import android.media.MediaDataSource
import android.media.MediaPlayer
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logi
import com.devrapid.musicdiskplayer.RotatedCircleWithIconImageView
import io.reactivex.Observable
import io.reactivex.Observer
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.PAUSE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.PLAYING
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.STOP
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * For handling MediaPlayer.
 *
 * Created by weian on 2017/6/18.
 */

@TargetApi(23)
class MediaPlayerAdapter(imageView: RotatedCircleWithIconImageView):
        IMultiMediaPlayer,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener {
    private var mImageView: RotatedCircleWithIconImageView
    private var mMediaPlayer = MediaPlayer()
    private var mState: EPlayerState = STOP
    private var getStreamingBufferPercentage: (percentage: Int) -> Unit = {}
    private lateinit var downloadModel: IMediaDownloader
    private var mObserver: Observer<Unit>? = null
    private var pauseTime: Int = 0
    private val MARSHMALLOW = android.os.Build.VERSION_CODES.M
    private val CURRENT_VERSION = android.os.Build.VERSION.SDK_INT
    private var durationListener: (duration: Int) -> Unit = {}

    init {
        mImageView = imageView
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setOnPreparedListener(this)
        mMediaPlayer.setOnErrorListener(this)
        mMediaPlayer.setOnCompletionListener(this)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        logd("start playing")
        mMediaPlayer.start()
        mImageView.start()
        mState = PLAYING
        durationListener(mp?.let { it.duration } ?: 0)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        getStreamingBufferPercentage(percent)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        val observable: Observable<Unit> = Observable.create({ subscriber ->
            subscriber.onNext(Unit)
            subscriber.onComplete()
        })

        mObserver?.let {
            observable.subscribe(it)
        }

        mObserver = null
    }

    /**
     * API
     */
    override fun playURL(url: String, observer: Observer<Unit>) {
        thread {
            try {
                val conn: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
                HttpURLConnection.setFollowRedirects(false)
                conn.requestMethod = "HEAD"
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    logi("URL is valid")
                }

                mMediaPlayer.let {
                    logd("prepare asynchronous thread")
                    // FIXME(Weian): can not get duration, MediaDataSource maybe have some issue. Disable MediaDataSource for a while.
                    // if (MARSHMALLOW <= CURRENT_VERSION) {
                    if (false) {
                        downloadModel = MediaDataSourceModel(url, object : IMediaDownloader.DownloadListener {
                            override fun onDownloadFinish() {
                                this@MediaPlayerAdapter.mMediaPlayer.prepareAsync()
                            }
                        })
                        downloadModel.let {
                            this@MediaPlayerAdapter.mMediaPlayer.setDataSource(it as MediaDataSource)
                        }
                    } else {
                        downloadModel = MediaTempFileModel(url, object : IMediaDownloader.DownloadListener {
                            override fun onDownloadFinish() {
                                this@MediaPlayerAdapter.mMediaPlayer.prepareAsync()
                            }
                        })
                        this@MediaPlayerAdapter.mMediaPlayer.setDataSource(MediaTempFileModel.TEMP)
                    }
                    downloadModel.start()
                    mObserver = observer
                }
            } catch (e: Exception) {
                loge(e.toString())
                loge("URL is not valid: $url")

            }
        }
    }

    override fun playLocal(path: String, observer: Observer<Unit>) {
        File(path).takeUnless { it.exists() }?.let {
            loge("File is not exist: $path")
            return
        }

        mMediaPlayer.setDataSource(path)
        mMediaPlayer.prepareAsync()
        mObserver = observer
    }

    override fun stop() {
        logd("stop player")
        mMediaPlayer.stop()
        mImageView.stop()
        mMediaPlayer.reset()
        mState = STOP
    }

    override fun pause() {
        logd("pause player")
        mMediaPlayer.pause()
        mImageView.stop()
        mState = PAUSE
    }

    override fun resume() {
        logd("resume player")
        mMediaPlayer.start()
        mImageView.start()
        mState = PLAYING
    }

    override fun replay(is_replay: Boolean) {
        logd("set replay: " + if (is_replay) " true" else "false")
        mMediaPlayer.isLooping = is_replay
    }

    override fun seekTo(sec: Int) {
        logd("seek time: " + sec)
        mMediaPlayer.seekTo(sec.times(1000))
    }

    override fun duration(): Int {
        logd("get duration of media")
        return mMediaPlayer.duration.div(1000)
    }

    override fun isReplay(): Boolean {
        logd("is replay: " + if (mMediaPlayer.isLooping) "true" else "false")
        return mMediaPlayer.isLooping
    }

    override fun isPlaying(): Boolean {
        logd("is playing: " + if (mMediaPlayer.isPlaying) " true" else " false")
        return mMediaPlayer.isPlaying
    }

    override fun current(callback: (time: Int) -> Unit) {
        logi("current time")
        callback(mMediaPlayer.currentPosition.div(1000))
    }

    override fun getState(): EPlayerState {
        return mState
    }

    override fun writeToFile(path: String) {
        if (!mMediaPlayer.isPlaying) return
        downloadModel.writeToFile(path)
    }

    override fun setDurationListener(listener: (duration: Int) -> Unit) {
        durationListener = listener
    }

    /*class Builder {
        var getCurrent: (time: Int) -> Unit = {}
        var getStreamingBufferPercentage: (percentage: Int) -> Unit = {}

        fun build(): MediaPlayerAdapter {
            val proxy = MediaPlayerAdapter()
            proxy.current(getCurrent)
            proxy.getStreamingBufferPercentage = this@Builder.getStreamingBufferPercentage
            return proxy
        }
    }*/
}