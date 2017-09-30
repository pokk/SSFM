package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchResultViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@Module
class SearchMusicFragmentModule {
    @Provides
    @PerFragment
    fun provideSearchUsecase(dataRepository: DataRepository): BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue> =
        SearchMusicCase(dataRepository)

    @Provides
    @PerFragment
    fun provideViewModel(searchUsecase: BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue>):
        FragmentSearchResultViewModel = FragmentSearchResultViewModel(searchUsecase)
}