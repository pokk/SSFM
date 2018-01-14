package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class Unknown private constructor() : PlayerMode() {
    private object Holder {
        val INSTANCE = Unknown()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val next get() = throw UnsupportedOperationException("There is no kind of operation")

    override val previous get() = throw UnsupportedOperationException("There is no kind of operation")
}