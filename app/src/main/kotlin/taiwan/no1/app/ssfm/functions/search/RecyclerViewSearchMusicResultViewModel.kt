package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayer
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicUsecase

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private val res: InfoBean,
                                             private val context: Context,
                                             private val getDetailMusicCase: FetchMusicDetailCase) :
    BaseObservable() {
    val songName by lazy { ObservableField<String>(res.songname ?: "") }
    val singerName by lazy { ObservableField<String>(res.singername ?: "") }
    val duration by lazy { ObservableField<String>(res.duration.toTimeString()) }

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        res.`_$320hash`?.let {
            getDetailMusicCase.execute(GetDetailMusicUsecase.RequestValue(it)) {
                onNext {
                    val player = MusicPlayer.instance
                    if (player.isPlaying()) player.stop()
                    it.data?.play_url?.let(player::play)
                }
            }
        }
    }

    fun optionClick(view: View) {
    }
    //endregion
}