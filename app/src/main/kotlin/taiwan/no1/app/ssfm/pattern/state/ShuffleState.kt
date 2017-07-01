package taiwan.no1.app.ssfm.pattern.state

import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.PlayerControllerLayout

/**
 * Shuffle state in controller panel.
 *
 * @author  jieyi
 * @since   7/1/17
 */
class ShuffleState: IControllerButtonState {
    override fun onclick(wrapper: PlayerControllerLayout) {
        wrapper.stateRecycleMode = RepeatState()
        wrapper.listImageButtons[0].imageResource = R.drawable.selector_controller_repeat
    }
}