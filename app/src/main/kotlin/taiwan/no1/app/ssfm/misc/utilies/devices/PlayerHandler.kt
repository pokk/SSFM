package taiwan.no1.app.ssfm.misc.utilies.devices

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import taiwan.no1.app.ssfm.misc.utilies.PausableTimer
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_LOOP_ONE
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_RANDOM

/**
 * For controlling media player.
 *
 * Created by weian on 2017/7/7.
 */

class PlayerHandler(player: IMultiMediaPlayer, playList: IPlayList) :
    IPlayerHandler {
    private var mPlayIndex: IPlayList
    private var mPlayer: IMultiMediaPlayer
    private var mPlayList: Array<String> = arrayOf()
    private var timer: PausableTimer? = null
    private val TAG = "PlayerHandler"

    init {
        mPlayer = player
        mPlayIndex = playList
    }

    override fun play(index: Int) {
        mPlayer.takeIf { it.isPlaying() }?.stop()

        val observer = object : Observer<Unit> {
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "observer onSubscribe")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "observer onError: " + e.message)
            }

            override fun onNext(t: Unit) {
                Log.i(TAG, "observer onNext")
                this@PlayerHandler.next()
            }

            override fun onComplete() {
                Log.i(TAG, "observer onComplete")
            }
        }

        if (mPlayList[index].startsWith("http://") ||
            mPlayList[index].startsWith("https://")) {
            // web address
            mPlayer.playURL(mPlayList[index], observer)
        }
        else {
            // local path
            mPlayer.playLocal(mPlayList[index], observer)
        }
        mPlayIndex.play(index)
    }

    override fun stop() {
        mPlayer.takeIf { it.isPlaying() }?.stop()
        timer?.stop()
    }

    override fun pause() {
        mPlayer.takeIf { it.isPlaying() }?.pause()
        timer?.pause()
    }

    override fun resume() {
        mPlayer.takeUnless { it.isPlaying() }?.resume()
        timer?.resume()
    }

    override fun seekTo(sec: Int) {
        mPlayer.seekTo(sec)
    }

    override fun duration(): Int = mPlayer.duration()

    override fun current(callback: (millisUntilFinished: Long) -> Unit) {
        timer = PausableTimer(duration().toLong() * 1000, 1 * 1000)
        timer?.onTick = callback
        timer?.start()
    }

    override fun isLooping() = PLAYLIST_STATE_LOOP_ONE == mPlayIndex.getState()

    override fun loopOne(is_loop: Boolean) {
        mPlayIndex.loopOne(is_loop)
    }

    override fun previous() {
        play(mPlayIndex.previous())
    }

    override fun next() {
        play(mPlayIndex.next())
    }

    override fun downloadProcess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loopAll(is_loop: Boolean) {
        mPlayIndex.loopAll(is_loop)
    }

    override fun random(is_random: Boolean) {
        mPlayIndex.random(is_random)
    }

    override fun musicState() = mPlayIndex.getState()

    override fun playerState() = mPlayer.getState()

    override fun nowPlaying() = mPlayIndex.nowPlaying()

    override fun isRandom() = PLAYLIST_STATE_RANDOM == mPlayIndex.getState()

    override fun setPlayList(list: Array<String>) {
        mPlayList = list
    }
}