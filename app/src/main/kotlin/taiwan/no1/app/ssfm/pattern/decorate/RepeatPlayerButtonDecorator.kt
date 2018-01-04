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
class RepeatPlayerButtonDecorator(btn: ImageButton) : PlayerButtonDecorator(btn) {
    init {
        btn.imageResource = R.drawable.selector_controller_repeat
    }

    override fun changeNextState(imageBtn: ImageButton) {
        // TODO: 7/2/17 Here should be state pattern.
        val listImagesResource = listOf(R.drawable.selector_controller_repeat,
                                        R.drawable.selector_controller_repeat_one,
                                        R.drawable.selector_controller_shuffle)

        state = state.plus(1).rem(listImagesResource.size)
        imageBtn.imageResource = listImagesResource[state]
    }
}