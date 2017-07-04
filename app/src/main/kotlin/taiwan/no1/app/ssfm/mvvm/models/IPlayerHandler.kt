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

    fun play()
    fun stop()
    fun pause()
    fun resume()
    fun previous()
    fun next()
    fun replay()
    fun seekTo()
    fun duration()
    fun downloadProcess()
    fun totalMusicTime()
    fun loopOne()
    fun loopAll()
    fun random()
    fun playerStatus()
    fun nowPlaying()
    fun isLooping()
    fun isRandom()
    fun restTime()
    fun setPlayList()
}