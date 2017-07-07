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

    override fun isLooping(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loopOne() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restTime() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun previous() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun next() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun downloadProcess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loopAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun random() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playerStatus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun nowPlaying() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRandom() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPlayList(list: Array<String>) {
        this.mPlayList = list
    }
}