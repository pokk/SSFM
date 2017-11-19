package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.misc.utilies.devices.ExoPlayerWrapper
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicCase

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private val res: InfoBean,
                                             private val context: Context,
                                             private val getDetailMusicCase: BaseUsecase<DetailMusicEntity, GetDetailMusicCase.RequestValue>) :
    BaseObservable() {
    val songName by lazy { ObservableField<String>(res.songname ?: "") }
    val singerName by lazy { ObservableField<String>(res.singername ?: "") }
    val duration by lazy { ObservableField<String>(res.duration.toTimeString()) }

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        res.`_$320hash`?.let {
            // TODO(jieyi): 11/19/17 The [player] shouldn't be here.
            val player = ExoPlayerWrapper(context, {}, {})
            getDetailMusicCase.execute(GetDetailMusicCase.RequestValue(it)) {
                onNext {
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