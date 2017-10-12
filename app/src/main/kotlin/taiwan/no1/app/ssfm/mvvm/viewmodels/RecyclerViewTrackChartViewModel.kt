package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import de.umass.lastfm.Track

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: Track, context: Context): BaseObservable() {
    val trackName by lazy { ObservableField<String>(item.name) }
    val artistName by lazy { ObservableField<String>(item.artist) }
}