package taiwan.no1.app.ssfm.pattern.state

import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.widget.PlayerControllerLayout

/**
 * Play state in controller panel.
 *
 * @author  jieyi
 * @since   7/1/17
 */
class PlayState: IControllerButtonState {
    override fun onclick(wrapper: PlayerControllerLayout) {
        wrapper.statePlayMode = PauseState()
        wrapper.listImageButtons[2].imageResource = R.drawable.selector_controller_play
    }
}