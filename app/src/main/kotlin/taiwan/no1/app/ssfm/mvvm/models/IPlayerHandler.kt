package taiwan.no1.app.ssfm.mvvm.models

/**
 * For controlling multi-media player and playlist.
 *
 * Created by weian on 2017/6/18.
 */
interface IPlayerHandler {
    enum class EPlayerState {
        EPlayerState_Playing,
        EPlayerState_Stop,
        EPLayerState_Pause
    }

    /**
     * real media player
     */
    fun play()
    fun stop()
    fun pause()
    fun resume()
    fun replay()
    fun seekTo()
    fun duration()
    fun current()
    fun isLooping()
    fun loopOne()
    fun restTime()

    /**
     * playlist
     */
    fun previous()
    fun next()
    fun downloadProcess()
    fun loopAll()
    fun random()
    fun playerStatus()
    fun nowPlaying()
    fun isRandom()
    fun setPlayList()
}