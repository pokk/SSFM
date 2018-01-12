package taiwan.no1.app.ssfm.misc.extension

import android.databinding.ObservableBoolean
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   2018/01/12
 */
fun ObservableBoolean.changeState(state: MusicPlayerState, currentIndex: Int, clickedIndex: Int) =
    when (state) {
        MusicPlayerState.Standby -> if (currentIndex != clickedIndex) set(false) else Unit
        MusicPlayerState.Play -> if (currentIndex == clickedIndex) set(true) else Unit
        else -> Unit
    }