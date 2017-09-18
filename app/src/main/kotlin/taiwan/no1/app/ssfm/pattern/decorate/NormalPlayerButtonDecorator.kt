package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton

/**
 * Decorator of normal player button without changing button image.
 *
 * @author  jieyi
 * @since   7/3/17
 */
class NormalPlayerButtonDecorator(val btn: ImageButton, setting: (Wrapper.() -> Unit)? = null):
    PlayerButtonDecorator(btn) {

    init {
        val wrapper = setting?.let(Wrapper()::also)

        wrapper?.imageResource?.let(btn::setImageResource)
        wrapper?.padding?.let { btn.setPadding(it, it, it, it) }
    }

    override fun changeNextState(imageBtn: ImageButton) {}

    data class Wrapper(
        var imageResource: Int? = null,
        var padding: Int? = null,
        var paddingStart: Int? = null,
        var paddingEnd: Int? = null,
        var paddingTop: Int? = null,
        var paddingBottom: Int? = null)
}