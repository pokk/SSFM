package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.misc.utilies.devices.annotations.MusicState
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant.MUSIC_STATE_PLAY
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant.MUSIC_STATE_STOP
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant.MUSIC_STATE_UNKNOWN

/**
 * @author  jieyi
 * @since   2017/12/02
 */
class MusicPlayerHelper private constructor() {
    lateinit var player: ExoPlayerWrapper
    var state = MUSIC_STATE_STOP
        @MusicState get() = when {
            player.isPlaying() -> MUSIC_STATE_PLAY
            else -> MUSIC_STATE_UNKNOWN
        }

    companion object {
        val instance: MusicPlayerHelper by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = MusicPlayerHelper()
    }

    fun hold(player: ExoPlayerWrapper) {
        this.player = player
    }
}