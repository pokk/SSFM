package taiwan.no1.app.ssfm.misc.utilies.devices

import android.annotation.TargetApi
import android.media.MediaPlayer
import android.webkit.URLUtil
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logi
import io.reactivex.Observable
import io.reactivex.Observer
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.PAUSE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.PLAYING
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState.STOP
import java.io.File

/**
 * For handling MediaPlayer.
 *
 * Created by weian on 2017/6/18.
 */

class MediaPlayerProxy: IMultiMediaPlayer,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnCompletionListener {
    private var mMediaPlayer = MediaPlayer()
    private var mState: EPlayerState = STOP
    private var getStreamingBufferPercentage: (percentage: Int) -> Unit = {}
    private lateinit var downloadModel: MediaDownloadModel
    private var mObserver: Observer<Unit>? = null
    private var pauseTime: Int = 0

    init {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setOnPreparedListener(this)
        mMediaPlayer.setOnErrorListener(this)
        mMediaPlayer.setOnCompletionListener(this)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        logd("start playing")
        mMediaPlayer.start()
        mState = PLAYING
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
    @TargetApi(23)
    override fun playURL(url: String, observer: Observer<Unit>) {
        if (URLUtil.isValidUrl(url)) {
            loge("URL is invalid: $url")
            return
        }

        mMediaPlayer.let {
            logd("prepare asynchronous thread")
            downloadModel = MediaDownloadModel(url, object: MediaDownloadModel.DownloadListener {
                override fun onDownloadFinish() {
                    this@MediaPlayerProxy.mMediaPlayer.prepareAsync()
                }
            })
            it.setDataSource(downloadModel)
            mObserver = observer
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
        mMediaPlayer.reset()
        mState = STOP
    }

    override fun pause() {
        logd("pause player")
        mMediaPlayer.pause()
        mState = PAUSE
    }

    override fun resume() {
        logd("resume player")
        mMediaPlayer.start()
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

    class Builder {
        var getCurrent: (time: Int) -> Unit = {}
        var getStreamingBufferPercentage: (percentage: Int) -> Unit = {}

        fun build(): MediaPlayerProxy {
            val proxy = MediaPlayerProxy()
            proxy.current(getCurrent)
            proxy.getStreamingBufferPercentage = this@Builder.getStreamingBufferPercentage
            return proxy
        }
    }
}