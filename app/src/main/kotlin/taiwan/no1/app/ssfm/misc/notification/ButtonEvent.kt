package taiwan.no1.app.ssfm.misc.notification

/**
 * Created by weian on 2018/1/6.
 *
 * The class is a callback function which gets the event of buttons from Notification.
 * onPlay: The user clicks the play button to play the music, and the button will become the pause button.
 * onPause: The user clicked the pause button to pause or stop the music for a while, and the button will become the play button.
 * onNext: The user clicked the next button to notify the music player to play the next music.
 */

private interface IButtonEvent {
    fun onPlay(): String
    fun onPause()
    fun onNext(): String
}

class ButtonEventFunc {
    var onPlay: (() -> String)? = null
    var onPause: (() -> Unit)? = null
    var onNext: (() -> String)? = null
}

class ButtonEvent(func: ButtonEventFunc.() -> Unit) : IButtonEvent {
    private var func = ButtonEventFunc().apply(func)

    override fun onPlay(): String {
        return func.onPlay?.invoke().orEmpty()
    }

    override fun onPause() {
        func.onPause?.invoke()
    }

    override fun onNext(): String {
        return func.onNext?.invoke().orEmpty()
    }
}