package taiwan.no1.app.ssfm.mvvm.models

import android.media.MediaPlayer
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.logi

/**
 * Created by weian on 2017/6/18.
 */

class MediaPlayerModel: IMultiMediaPlayer,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private var mMediaPlayer: MediaPlayer ?= null
    private var mState: IPlayerHander.EPlayerState = IPlayerHander.EPlayerState.EPlayerState_Stop

    constructor() {
        this.mMediaPlayer = MediaPlayer()
        this.mMediaPlayer?.setOnPreparedListener(this)
        this.mMediaPlayer?.setOnErrorListener(this)
    }


    override fun onPrepared(mp: MediaPlayer?) {
        logd("start playing")
        this.mMediaPlayer?.start()
        this.mState = IPlayerHander.EPlayerState.EPlayerState_Playing
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    /**
     * API
     */

    override fun play(uri: String) {
        this.mMediaPlayer?.let {
            logd("prepare asynchronized thread")
            it.setDataSource(uri)
            it.prepareAsync()
        }
    }

    override fun stop() {
        logd("stop player")
        this.mMediaPlayer?.stop()
        this.mState = IPlayerHander.EPlayerState.EPlayerState_Stop
    }

    override fun pause() {
        logd("pause player")
        this.mMediaPlayer?.pause()
        this.mState = IPlayerHander.EPlayerState.EPLayerState_Pause
    }

    override fun resume() {
        logd("resume player")
        this.mMediaPlayer?.start()
        this.mState = IPlayerHander.EPlayerState.EPlayerState_Playing
    }

    override fun replay(is_replay: Boolean) {
        logd("set replay: " + if (is_replay) " true" else "false")
        this.mMediaPlayer?.isLooping = is_replay
    }

    override fun seekto(sec: Int) {
        logd("seek time: " + sec)
        this.mMediaPlayer?.seekTo(sec * 1000)
    }

    override fun duration(): Int {
        logd("get duration of media")
        return this.mMediaPlayer?.let {
            it.duration / 1000
        } ?: -1
    }

    override fun isReplay(): Boolean {
        logd("is replay: " + this.mMediaPlayer?.let {
            if (it.isLooping)
                "true"
            else
                "false"
        })
        return this.mMediaPlayer?.isLooping ?: false
    }

    override fun isPlaying(): Boolean {
        logd("is playing: " + this.mMediaPlayer?.let{
            if (it.isPlaying)
                "true"
            else
                "false"
        })
        return this.mMediaPlayer?.isPlaying ?: false
    }

    override fun current(): Int {
        logi("current time")
        return this.mMediaPlayer?.let {
            it.currentPosition / 1000
        } ?: 0
    }

    override fun getState(): IPlayerHander.EPlayerState {
        return this.mState
    }
}