package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music

import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.mode.PlayerMode

/**
 * @author  jieyi
 * @since   1/14/18
 */
class StateChanger(private var state: PlayerMode) {
    fun setState(state: PlayerMode) {
        this.state = state
    }

    fun next() = state.next

    fun previous() = state.previous
}