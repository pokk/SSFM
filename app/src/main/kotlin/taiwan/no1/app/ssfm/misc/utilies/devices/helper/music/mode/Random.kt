package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class Random : PlayerMode() {
    private object Holder {
        val INSTANCE = Random()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val next get() = playlistManager.random

    override val previous get() = playlistManager.random
}