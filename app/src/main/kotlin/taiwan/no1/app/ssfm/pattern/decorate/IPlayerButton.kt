package taiwan.no1.app.ssfm.pattern.decorate

import android.widget.ImageButton

/**
 * Interface of the actions of media player button.
 *
 * @author  jieyi
 * @since   7/2/17
 */
interface IPlayerButton {
    fun changeNextState(imageBtn: ImageButton)
    fun detach()
}