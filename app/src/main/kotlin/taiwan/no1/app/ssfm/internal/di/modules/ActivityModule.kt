package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@Module
class ActivityModule {
    // TODO: 6/15/17 TEST CODE!! IT WILL BE DELETED.
    @PerActivity
    @Provides
    fun provideName(): String = "TEST"
}