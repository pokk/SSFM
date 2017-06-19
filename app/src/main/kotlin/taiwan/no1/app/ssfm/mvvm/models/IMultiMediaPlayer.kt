package taiwan.no1.app.ssfm.mvvm.models

/**
 * For controlling multi-media player
 *
 * Created by weian on 2017/6/18.
 */
interface IMultiMediaPlayer {
    fun play(uri: String)
    fun stop()
    fun pause()
    fun resume()
    fun replay(is_replay: Boolean)
    fun seekto(sec: Int)
    fun duration(): Int
    fun isReplay(): Boolean
    fun isPlaying(): Boolean
}