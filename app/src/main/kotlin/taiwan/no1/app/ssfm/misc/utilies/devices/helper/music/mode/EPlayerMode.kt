package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
enum class EPlayerMode(val playerMode: PlayerMode) {
    PLAYLIST_STATE_UNKNOWN(Unknown.instance),
    PLAYLIST_STATE_NORMAL(Normal.instance),
    PLAYLIST_STATE_RANDOM(Random.instance),
    PLAYLIST_STATE_LOOP_ONE(LoopOne.instance),
    PLAYLIST_STATE_LOOP_ALL(LoopAll.instance)
}