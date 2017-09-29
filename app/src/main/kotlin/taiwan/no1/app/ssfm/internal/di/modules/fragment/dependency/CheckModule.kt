package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
@Module
class CheckModule {
    @Provides
    @PerFragment
    fun provideTestString(): String = "WWWWWWTTTTTTTFFFFFFFFFFFFF"
}