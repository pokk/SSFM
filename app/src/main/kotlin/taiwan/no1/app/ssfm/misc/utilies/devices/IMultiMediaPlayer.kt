package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler.EPlayerState

/**
 * For controlling multi-media player.
 *
 * Created by weian on 2017/6/18.
 */
interface IMultiMediaPlayer {
    fun play(uri: String)
    fun stop()
    fun pause()
    fun resume()
    fun replay(is_replay: Boolean)
    fun seekTo(sec: Int)
    fun current(callback: (time: Int) -> Unit)
    fun duration(): Int
    fun isReplay(): Boolean
    fun isPlaying(): Boolean
    fun getState(): EPlayerState
    fun writeToFile(path: String)
}