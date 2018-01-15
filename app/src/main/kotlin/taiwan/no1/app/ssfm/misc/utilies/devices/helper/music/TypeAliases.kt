@file:Suppress("NOTHING_TO_INLINE")

package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistTrackUriManager
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   1/14/18
 */
typealias stateChangedListener = ((state: MusicPlayerState) -> Unit)?

val playerHelper by lazy { MusicPlayerHelper.instance }
val playlistManager by lazy { PlaylistTrackUriManager.instance }