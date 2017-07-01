package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton
import org.jetbrains.anko.imageResource
import taiwan.no1.app.ssfm.R

/**
 * Decorate to media repeat button.
 *
 * @author  jieyi
 * @since   7/2/17
 */
class RepeatButtonDecorator(btn: ImageButton): BaseButtonDecorator(btn) {
    init {
        btn.imageResource = R.drawable.selector_controller_repeat
    }

    override fun changeNextState(imageBtn: ImageButton) {
        // TODO: 7/2/17 Here should be state pattern.
        state = state.plus(1).rem(3)
        when (state) {
            0 -> imageBtn.imageResource = R.drawable.selector_controller_repeat
            1 -> imageBtn.imageResource = R.drawable.selector_controller_repeat_one
            2 -> imageBtn.imageResource = R.drawable.selector_controller_shuffle
        }
    }
}