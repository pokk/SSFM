package taiwan.no1.app.ssfm.pattern.state

import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.widget.PlayerControllerLayout

/**
 * Repeat only one music state in controller panel.
 *
 * @author  jieyi
 * @since   7/1/17
 */
class RepeatOneState: IControllerButtonState {
    override fun onclick(wrapper: PlayerControllerLayout) {
        wrapper.stateRecycleMode = ShuffleState()
        wrapper.listImageButtons[0].imageResource = R.drawable.selector_controller_shuffle
    }
}