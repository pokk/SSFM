package taiwan.no1.app.ssfm.misc.utilies.devices

import android.media.MediaPlayer
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.logi

/**
 * For handling MediaPlayer.
 *
 * Created by weian on 2017/6/18.
 */

class MediaPlayerProxy: IMultiMediaPlayer,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener {
    private var mMediaPlayer = MediaPlayer()
    private var mState: IPlayerHandler.EPlayerState = IPlayerHandler.EPlayerState.EPlayerState_Stop
    private var getStreamingBufferPercentage: (percentage: Int) -> Unit = {}

    init {
        this.mMediaPlayer = MediaPlayer()
        this.mMediaPlayer.setOnPreparedListener(this)
        this.mMediaPlayer.setOnErrorListener(this)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        logd("start playing")
        this.mMediaPlayer.start()
        this.mState = IPlayerHandler.EPlayerState.EPlayerState_Playing
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        this.getStreamingBufferPercentage(percent)
    }

    /**
     * API
     */
    override fun play(uri: String) {
        this.mMediaPlayer.let {
            logd("prepare asynchronous thread")
            it.setDataSource(uri)
            it.prepareAsync()
        }
    }

    override fun stop() {
        logd("stop player")
        this.mMediaPlayer.stop()
        this.mState = IPlayerHandler.EPlayerState.EPlayerState_Stop
    }

    override fun pause() {
        logd("pause player")
        this.mMediaPlayer.pause()
        this.mState = IPlayerHandler.EPlayerState.EPLayerState_Pause
    }

    override fun resume() {
        logd("resume player")
        this.mMediaPlayer.start()
        this.mState = IPlayerHandler.EPlayerState.EPlayerState_Playing
    }

    override fun replay(is_replay: Boolean) {
        logd("set replay: " + if (is_replay) " true" else "false")
        this.mMediaPlayer.isLooping = is_replay
    }

    override fun seekTo(sec: Int) {
        logd("seek time: " + sec)
        this.mMediaPlayer.seekTo(sec.times(1000))
    }

    override fun duration(): Int {
        logd("get duration of media")
        return this.mMediaPlayer.duration.div(1000)
    }

    override fun isReplay(): Boolean {
        logd("is replay: " + if (this.mMediaPlayer.isLooping ?: false) "true" else "false")
        return this.mMediaPlayer.isLooping ?: false
    }

    override fun isPlaying(): Boolean {
        logd("is playing: " + if (this.mMediaPlayer.isPlaying) " true" else " false")
        return this.mMediaPlayer.isPlaying
    }

    override fun current(callback: (time: Int) -> Unit) {
        logi("current time")
        callback(this.mMediaPlayer.currentPosition.div(1000))
    }

    override fun getState(): IPlayerHandler.EPlayerState {
        return this.mState
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