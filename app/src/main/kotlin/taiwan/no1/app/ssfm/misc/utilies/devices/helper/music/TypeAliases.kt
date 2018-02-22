package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import android.os.Environment
import taiwan.no1.app.ssfm.misc.utilies.devices.manager.PlaylistElementManager
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   1/14/18
 */
val playerHelper by lazy { MusicPlayerHelper.instance }
val playlistManager by lazy { PlaylistElementManager.instance }

typealias stateChangedListener = ((state: MusicPlayerState) -> Unit)?

fun musicDir(uri: String) = run {
    val fileName = uri.split("/").lastOrNull()
    arrayOf(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_MUSIC, fileName).joinToString("/")
}
