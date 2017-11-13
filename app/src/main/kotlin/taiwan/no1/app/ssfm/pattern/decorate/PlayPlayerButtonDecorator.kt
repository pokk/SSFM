package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton
import android.widget.ImageView
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.padding
import taiwan.no1.app.ssfm.R

/**
 * Decorate to media play button.
 *
 * @author  jieyi
 * @since   7/2/17
 */
class PlayPlayerButtonDecorator(btn: ImageButton) : PlayerButtonDecorator(btn) {
    init {
        btn.apply {
            imageResource = R.drawable.selector_controller_play
            padding = 0
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    override fun changeNextState(imageBtn: ImageButton) {
        // TODO: 7/2/17 Here should be state pattern.
        val listImagesResource = listOf(R.drawable.selector_controller_play,
            R.drawable.selector_controller_pause)

        state = state.plus(1).rem(listImagesResource.size)
        imageBtn.imageResource = listImagesResource[state]
    }
}