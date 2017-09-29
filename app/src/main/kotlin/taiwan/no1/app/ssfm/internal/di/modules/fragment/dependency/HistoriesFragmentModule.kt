package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchHistoryViewModel
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchHistoryFragment

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@Module
class HistoriesFragmentModule {
    @Provides
    @PerFragment
    fun provideGetUsecase(dataRepository: DataRepository): BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue> =
        GetKeywordHistoriesCase(dataRepository)

    @Provides
    @PerActivity
    fun provideDeleteUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue> =
        RemoveKeywordHistoriesCase(dataRepository)

    @Provides
    @PerFragment
    fun provideViewModel(fragment: SearchHistoryFragment,
                         getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>,
                         deleteHistoriesUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>):
        FragmentSearchHistoryViewModel = FragmentSearchHistoryViewModel(fragment,
        getHistoriesUsecase,
        deleteHistoriesUsecase)
}