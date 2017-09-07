package taiwan.no1.app.ssfm.misc.utilies.devices

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import taiwan.no1.app.ssfm.misc.utilies.PausableTimer

/**
 * For controlling media player.
 *
 * Created by weian on 2017/7/7.
 */

class PlayerHandler(p0: IMultiMediaPlayer, p1: IPlayList): IPlayerHandler {
    private var mPlayIndex: IPlayList
    private var mPlayer: IMultiMediaPlayer
    private var mPlayList: Array<String> = arrayOf()
    //private var timer by lazy { PausableTimer() }
    private lateinit var timer: PausableTimer
    private val TAG = "PlayerHandler"

    init {
        this.mPlayer = p0
        this.mPlayIndex = p1
    }

    override fun play(index: Int) {
        this.mPlayer.takeIf { it.isPlaying() }?.stop()

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

    override fun isLooping(): Boolean = this.mPlayIndex.isLoopOne()

    override fun loopOne(is_loop: Boolean) { this.mPlayIndex.loopOne(is_loop) }

    override fun restTime(): Int = this.mPlayer.takeIf { it.isPlaying() }.let {
        // FIXME(jieyi): 8/14/17 Temporally set default value. Vivian, please fix it.
//        it?.duration()?.minus(it.current()) ?: 0
        0
    }

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

    override fun playerStatus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun nowPlaying(): Int = this.mPlayIndex.nowPlaying()

    override fun isRandom(): Boolean = this.mPlayIndex.isRandom()

    override fun setPlayList(list: Array<String>) {
        this.mPlayList = list
    }
}