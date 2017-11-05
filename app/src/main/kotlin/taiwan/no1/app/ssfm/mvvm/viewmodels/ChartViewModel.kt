package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopTracksCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartViewModel(private val context: Context,
                     private val artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                     private val artistTopTracksUsecase: BaseUsecase<ArtistTopTrackEntity, GetArtistTopTracksCase.RequestValue>,
                     private val artistTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsCase.RequestValue>):
    BaseViewModel() {
    /** Menu Title */
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_charts)) }
    /** The keyword of a song or singer's name */
    val keyword by lazy { ObservableField<String>("") }
    /** Check search view is clicked or un-clicked */
    val isSearching by lazy { ObservableBoolean() }
    val collapseView by lazy { ObservableBoolean() }

    //region Action from View
    /**
     * The action of closing the search view.
     */
    fun closeSearchView(): Boolean {
        collapseSearchView()
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @param view
     */
    fun openSearchView(view: View?) {
        collapseSearchView(false)
    }

    /**
     * The action of submitting the search query.
     *
     * @param query the query of song's or singer's name for searching a music.
     */
    fun querySubmit(query: String): Boolean {
        return true
    }

    /**
     * The action of the search query's newText is changed.
     *
     * @param newText inputting by the user in the search view.
     */
    fun textChanged(newText: String): Boolean {
        return true
    }
    //endregion

    fun collapseSearchView(collapse: Boolean = true) {
        isSearching.set(!collapse)
    }
}