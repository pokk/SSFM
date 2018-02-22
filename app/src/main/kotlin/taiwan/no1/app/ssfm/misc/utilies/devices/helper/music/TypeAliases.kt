package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistElementManager
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   1/14/18
 */
val playerHelper by lazy { MusicPlayerHelper.instance }
val playlistManager by lazy { PlaylistElementManager.instance }

typealias stateChangedListener = ((state: MusicPlayerState) -> Unit)?
