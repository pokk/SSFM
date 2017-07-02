package taiwan.no1.app.ssfm.pattern.decorate

import android.graphics.Color
import android.widget.ImageButton
import android.widget.ImageView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Base setting of a decorator of media player button.
 *
 * @author  jieyi
 * @since   7/2/17
 */
abstract class PlayerButtonDecorator(protected val imageButton: ImageButton): IPlayerButton {
    open var listener: (btn: ImageButton) -> Unit = { _: ImageButton -> }
    open var state: Int = 0

    init {
        this.imageButton.also {
            it.backgroundColor = Color.TRANSPARENT
            it.scaleType = ImageView.ScaleType.FIT_CENTER
            it.padding = 20
            it.onClick {
                this@PlayerButtonDecorator.listener(imageButton)
                this@PlayerButtonDecorator.changeNextState(this@PlayerButtonDecorator.imageButton)
            }
        }
    }

    override fun detach() {
        this.imageButton.setOnClickListener(null)
    }

    override abstract fun changeNextState(imageBtn: ImageButton)
}