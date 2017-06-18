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
    fun replay()
    fun seekto()
    fun duration()
    fun setLoop(is_loop: Boolean)
    fun isLooping(): Boolean
}