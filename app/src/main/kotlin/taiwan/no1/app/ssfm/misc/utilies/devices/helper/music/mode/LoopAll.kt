package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class LoopAll private constructor() : PlayerMode() {
    private object Holder {
        val INSTANCE = LoopAll()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val next get() = playlistManager.loopingNext

    override val previous get() = playlistManager.loopingPrevious
}