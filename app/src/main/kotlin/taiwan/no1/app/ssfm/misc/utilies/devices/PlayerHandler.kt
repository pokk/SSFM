package taiwan.no1.app.ssfm.misc.utilies.devices

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import taiwan.no1.app.ssfm.misc.utilies.PausableTimer
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.EMusicState_LoopOne
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.EMusicState_Random

/**
 * For controlling media player.
 *
 * Created by weian on 2017/7/7.
 */

class PlayerHandler: IPlayerHandler {
    private var mPlayIndex: IPlayList
    private var mPlayer: IMultiMediaPlayer
    private var mPlayList: Array<String> = arrayOf()
    private lateinit var timer: PausableTimer
    private val TAG = "PlayerHandler"

    constructor(player: IMultiMediaPlayer, playList: IPlayList) {
        this.mPlayer = player
        this.mPlayIndex = playList
    }

    override fun play(index: Int) {
        this.mPlayer.takeIf { it.isPlaying() }?.stop()

        // TODO(jieyi): 9/9/17 Obtain an observable and connect the observer with the observable.

        val observer = object: Observer<Unit> {
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

        if (this.mPlayList[index].startsWith("http://") ||
            this.mPlayList[index].startsWith("https://")) {
            // web address
            this.mPlayer.playURL(this.mPlayList[index], observer)
        } else {
            // local path
            this.mPlayer.playLocal(this.mPlayList[index], observer)
        }
        this.mPlayIndex.play(index)
    }

    override fun stop() {
        this.mPlayer.takeIf { it.isPlaying() }?.stop()
        this.timer.stop()
    }

    override fun pause() {
        this.mPlayer.takeIf { it.isPlaying() }?.pause()
        this.timer.pause()
    }

    override fun resume() {
        this.mPlayer.takeIf { it.isPlaying() }?.resume()
        this.timer.resume()
    }

    override fun seekTo(sec: Int) {
        this.mPlayer.seekTo(sec)
    }

    override fun duration(): Int = this.mPlayer.duration()

    override fun current(callback: (millisUntilFinished: Long) -> Unit) {
        this.timer = PausableTimer(this.duration().toLong() * 1000, 1 * 1000)
        this.timer.onTick = callback
        this.timer.start()
    }

    override fun isLooping(): Boolean =
            this.mPlayIndex.getState() == EMusicState_LoopOne

    override fun loopOne(is_loop: Boolean) { this.mPlayIndex.loopOne(is_loop) }

    override fun previous() {
        play(this.mPlayIndex.previous())
    }

    override fun next() {
        play(this.mPlayIndex.next())
    }

    override fun downloadProcess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loopAll(is_loop: Boolean) {
        this.mPlayIndex.loopAll(is_loop)
    }

    override fun random(is_random: Boolean) {
        this.mPlayIndex.random(is_random)
    }

    override fun playerStatus(): EMusicState = this.mPlayIndex.getState()

    override fun nowPlaying(): Int = this.mPlayIndex.nowPlaying()

    override fun isRandom(): Boolean =
            this.mPlayIndex.getState() == EMusicState_Random

    override fun setPlayList(list: Array<String>) {
        this.mPlayList = list
    }
}