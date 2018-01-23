package taiwan.no1.app.ssfm.misc.utilies.devices.manager

import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity

/**
 * @author  jieyi
 * @since   2018/01/20
 */
class PlaylistElementManager private constructor() : PlaylistManager<PlaylistItemEntity>() {
    private object Holder {
        val INSTANCE = PlaylistElementManager()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    override val playlist by lazy { mutableListOf<PlaylistItemEntity>() }
}