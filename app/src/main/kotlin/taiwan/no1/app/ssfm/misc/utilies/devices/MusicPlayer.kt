package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.misc.extension.gContext
import weian.cheng.mediaplayerwithexoplayer.ExoPlayerWrapper

/**
 * @author  jieyi
 * @since   11/19/17
 */
class MusicPlayer private constructor() {
    companion object {
        val instance by lazy {}
    }

    private object Holder {
        val INSTANCE = ExoPlayerWrapper(gContext())
    }
}
