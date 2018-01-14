package taiwan.no1.app.ssfm.misc.utilies.devices.manager

/**
 * @author  jieyi
 * @since   1/13/18
 */
class PlaylistTrackUriManager private constructor() : PlaylistManager<String>() {
    private object Holder {
        val INSTANCE = PlaylistTrackUriManager()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val playlist by lazy { mutableListOf<String>() }
}