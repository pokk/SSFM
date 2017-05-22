package taiwan.no1.app.ssfm.mvvm.models.data.remote

import taiwan.no1.app.ssfm.mvvm.models.data.remote.config.IApiConfig
import taiwan.no1.app.ssfm.mvvm.models.data.remote.config.Music1Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that creates different implementations of [taiwan.no1.app.ssfm.mvvm.models.data.remote.config.IApiConfig].
 *
 * @author  jieyi
 * @since   5/10/17
 */
@Singleton
class RestfulApiFactory @Inject constructor() {

    /**
     * Create a new http service configuration.
     *
     * @return Music http service config.
     */
    fun createMusic1Config(): IApiConfig = Music1Config()

    fun createMusic2Config(): IApiConfig = Music1Config()
}