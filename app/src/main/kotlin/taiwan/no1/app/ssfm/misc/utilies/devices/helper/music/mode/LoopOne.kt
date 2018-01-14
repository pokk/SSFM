package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class LoopOne private constructor() : PlayerMode() {
    private object Holder {
        val INSTANCE = LoopOne()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val next get() = playlistManager.again

    override val previous get() = playlistManager.again
}