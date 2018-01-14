package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class Normal : PlayerMode() {
    private object Holder {
        val INSTANCE = Normal()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val next get() = playlistManager.next

    override val previous get() = playlistManager.previous
}