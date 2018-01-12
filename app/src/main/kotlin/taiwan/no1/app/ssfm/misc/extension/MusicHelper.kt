package taiwan.no1.app.ssfm.misc.extension

import android.databinding.ObservableBoolean
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Pause
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Play
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Standby

/**
 * @author  jieyi
 * @since   2018/01/12
 */
fun ObservableBoolean.changeState(state: MusicPlayerState, currentIndex: Int, clickedIndex: Int) =
    when (state) {
        Standby -> if (currentIndex != clickedIndex) set(false) else Unit
        Play -> if (currentIndex == clickedIndex) set(true) else Unit
        Pause -> set(false)
        else -> Unit
    }