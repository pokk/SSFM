package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode

import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistManager

/**
 * @author  jieyi
 * @since   1/14/18
 */
abstract class PlayerMode {
    protected lateinit var playlistManager: PlaylistManager<String>

    fun hold(manager: PlaylistManager<String>) {
        playlistManager = manager
    }

    abstract val next: String?
    abstract val previous: String?
}