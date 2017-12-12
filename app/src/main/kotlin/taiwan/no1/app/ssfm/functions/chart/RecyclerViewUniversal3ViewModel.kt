package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal3ViewModel(val item: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val trackName by lazy { ObservableField<String>() }
    val ranking by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    val isPlaying by lazy { ObservableBoolean() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                textBackground.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
            }
            false
        }
    }

    init {
        (item as TrackEntity.Track).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == item.url)
            artistName.set(it.artist?.name)
            trackName.set(it.name)
            ranking.set(it.attr?.rank.orEmpty())
            duration.set(it.duration?.toInt()?.toTimeString())
            thumbnail.set(item.images?.get(LARGE)?.text.orEmpty())
        }
    }

    //region Lifecycle
    override fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>) {
        super.onAttach(lifecycleProvider)
        RxBus.get().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        RxBus.get().unregister(this)
    }
    //endregion

    @Subscribe(tags = [(Tag(VIEWMODEL_CHART_DETAIL_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as TrackEntity.Track).url) isPlaying.set(false)
    }

    fun itemOnClick(view: View) {
        isPlaying.set(!isPlaying.get())
        (item as TrackEntity.Track).run {
            RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, url)
            // TODO(jieyi): 2017/12/12
            MusicPlayerHelper.instance.play(url ?: "") {
                //                lifecycleProvider.execute(addPlaylistItemCase,
//                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong(),
//                        trackUri = url,
//                        trackName = title,
//                        artistName = artist,
//                        coverUrl = coverURL,
//                        lyricUrl = lyricURL,
//                        duration = length))) { onNext { logw(it) } }
            }
        }
    }
}