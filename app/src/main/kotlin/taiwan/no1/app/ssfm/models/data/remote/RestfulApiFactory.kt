package taiwan.no1.app.ssfm.models.data.remote

import taiwan.no1.app.ssfm.internal.di.annotations.scopes.Network
import taiwan.no1.app.ssfm.models.data.remote.config.IApiConfig
import taiwan.no1.app.ssfm.models.data.remote.config.Music1Config
import taiwan.no1.app.ssfm.models.data.remote.config.Music2Config
import taiwan.no1.app.ssfm.models.data.remote.config.Music3Config
import javax.inject.Inject

/**
 * Factory that creates different implementations of [IApiConfig].
 *
 * @author  jieyi
 * @since   5/10/17
 */
@Network
class RestfulApiFactory @Inject constructor() {
    /**
     * Create a new http service configuration.
     *
     * @return Music http service config.
     */
    fun createMusic1Config(): IApiConfig = Music1Config()

    /**
     * Create a new http service configuration.
     *
     * @return Music http service config.
     */
    fun createMusic2Config(): IApiConfig = Music2Config()

    /**
     * Create a new http service configuration.
     *
     * @return Music http service config.
     */
    fun createMusic3Config(): IApiConfig = Music3Config()
}