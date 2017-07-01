package taiwan.no1.app.ssfm.pattern.state

import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.PlayerControllerLayout

/**
 * Repeat all music state in controller panel.
 *
 * @author  jieyi
 * @since   7/1/17
 */
class RepeatState: IControllerButtonState {
    override fun onclick(wrapper: PlayerControllerLayout) {
        wrapper.stateRecycleMode = RepeatOneState()
        wrapper.listImageButtons[0].imageResource = R.drawable.selector_controller_repeat_one
    }
}