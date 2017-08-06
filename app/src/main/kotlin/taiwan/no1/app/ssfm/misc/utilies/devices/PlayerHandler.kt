package taiwan.no1.app.ssfm.misc.utilies.devices

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

    init {
        this.mPlayer = p0
        this.mPlayIndex = p1
    }

    override fun play() {
        // TODO(jieyi): 7/28/17 Let callback function run by using timer.
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.play(this.mPlayList[this.mPlayIndex.nowPlaying()])
        }
//        this.mPlayer.takeIf { it.isPlaying() }?.play(this.mPlayList[this.mPlayIndex.nowPlaying()])
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

    override fun isLooping(): Boolean = this.mPlayer.isReplay()

    override fun loopOne(is_loop: Boolean) {
        this.mPlayer.replay(is_loop)
    }

    override fun restTime(): Int = this.mPlayer.takeIf { it.isPlaying() }.let {
        it?.duration()?.minus(it.current()) ?: 0
    }

    override fun previous() {
        this.mPlayer.play(this.mPlayList[this.mPlayIndex.previous()])
    }

    override fun next() {
        this.mPlayer.play(this.mPlayList[this.mPlayIndex.next()])
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