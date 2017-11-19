package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.App

/**
 * @author  jieyi
 * @since   11/19/17
 */
class MusicPlayer private constructor() {
    private object Holder {
        val INSTANCE = ExoPlayerWrapper(App.appComponent.context(), {}, {})
    }

    companion object {
        val instance: ExoPlayerWrapper by lazy { Holder.INSTANCE }
    }
}
