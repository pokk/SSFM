package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton
import org.jetbrains.anko.imageResource

/**
 * Decorator of normal player button without changing button image.
 *
 * @author  jieyi
 * @since   7/3/17
 */
class NormalPlayerButtonDecorator(val btn: ImageButton,
                                  setting: Wrapper.() -> Unit): PlayerButtonDecorator(btn) {
    init {
        val wrapper = Wrapper().also(setting)
        wrapper.imageResource?.let { this.btn.imageResource = it }
    }

    override fun changeNextState(imageBtn: ImageButton) {}

    data class Wrapper(val imageResource: Int? = null)
}