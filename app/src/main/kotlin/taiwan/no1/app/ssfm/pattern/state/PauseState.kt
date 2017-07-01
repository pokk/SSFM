package taiwan.no1.app.ssfm.pattern.state

import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.PlayerControllerLayout

/**
 * Pause state in controller panel.
 *
 * @author  jieyi
 * @since   7/1/17
 */
class PauseState: IControllerButtonState {
    override fun onclick(wrapper: PlayerControllerLayout) {
        wrapper.statePlayMode = PlayState()
        wrapper.listImageButtons[2].imageResource = R.drawable.selector_controller_pause
    }
}