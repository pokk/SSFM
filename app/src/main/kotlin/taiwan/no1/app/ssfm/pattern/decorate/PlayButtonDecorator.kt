package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.padding
import taiwan.no1.app.ssfm.R

/**
 * Decorate to media play button.
 *
 * @author  jieyi
 * @since   7/2/17
 */
class PlayButtonDecorator(btn: ImageButton): BaseButtonDecorator(btn) {
    init {
        btn.apply {
            imageResource = R.drawable.selector_controller_play
            padding = 0
        }
    }

    override fun changeNextState(imageBtn: ImageButton) {
        // TODO: 7/2/17 Here should be state pattern.
        state = state.plus(1).rem(2)
        when (state) {
            0 -> imageBtn.imageResource = R.drawable.selector_controller_play
            1 -> imageBtn.imageResource = R.drawable.selector_controller_pause
        }
    }
}