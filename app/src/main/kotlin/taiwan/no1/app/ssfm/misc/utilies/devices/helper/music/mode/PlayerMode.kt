package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistManager
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity

/**
 * @author  jieyi
 * @since   1/14/18
 */
abstract class PlayerMode {
    protected lateinit var playlistManager: PlaylistManager<PlaylistItemEntity>

    fun hold(manager: PlaylistManager<PlaylistItemEntity>) {
        playlistManager = manager
    }

    abstract val next: PlaylistItemEntity?
    abstract val previous: PlaylistItemEntity?
}