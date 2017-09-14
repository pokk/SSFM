package taiwan.no1.app.ssfm.misc.utilies.devices

/**
 * For controlling multi-media player and playlist.
 *
 * Created by weian on 2017/6/18.
 */
interface IPlayerHandler {
    enum class EPLAYERSTATE {
        PLAYING,
        STOP,
        PAUSE
    }

    /**
     * real media player
     */
    fun play(index: Int = 0)
    fun stop()
    fun pause()
    fun resume()
    fun seekTo(sec: Int)
    fun duration(): Int
    fun current(callback: (millisUntilFinished: Long) -> Unit)
    fun isLooping(): Boolean
    fun loopOne(is_loop: Boolean)

    /**
     * playlist
     */
    fun previous()
    fun next()
    fun downloadProcess()
    fun loopAll(is_loop: Boolean)
    fun random(is_random: Boolean)
    fun playerStatus(): IPlayList.EMUSICSTATE
    fun nowPlaying(): Int
    fun isRandom(): Boolean
    fun setPlayList(list: Array<String>)
}