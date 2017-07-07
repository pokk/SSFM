package taiwan.no1.app.ssfm.mvvm.models

/**
 * for controlling media player
 *
 * Created by weian on 2017/7/7.
 */

class PlayerHandler: IPlayerHandler {
    private var mPlayIndex: IPlayList = PlayListModel()
    private var mPlayer: IMultiMediaPlayer = MediaPlayerModel()
    private var mPlayList: Array<String> = arrayOf()

    override fun play() {
        this.mPlayer.takeIf { it.isPlaying() }?.play(this.mPlayList[this.mPlayIndex.nowPlaying()])
    }

    override fun stop() {
        this.mPlayer.takeIf { it.isPlaying() }?.stop()
    }

    override fun pause() {
        this.mPlayer.takeIf { it.isPlaying() }?.pause()
    }

    override fun resume() {
        this.mPlayer.takeIf { it.isPlaying() }?.resume()
    }

    override fun seekTo(sec: Int) {
        this.mPlayer.seekTo(sec)
    }

    override fun duration(): Int = this.mPlayer.duration()

    override fun current(): Int = this.mPlayer.takeIf { it.isPlaying() }?.current() ?: 0

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