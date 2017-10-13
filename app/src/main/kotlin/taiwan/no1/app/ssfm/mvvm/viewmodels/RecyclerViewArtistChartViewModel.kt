package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import de.umass.lastfm.Artist

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewArtistChartViewModel(val item: Artist, context: Context): BaseObservable() {
    val artistName by lazy { ObservableField<String>(item.name) }
}