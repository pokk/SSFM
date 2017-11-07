package taiwan.no1.app.ssfm.models.data.remote.config

/**
 * Interface of the setting of the difference http configurations.
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IApiConfig {
    /**
     * Obtain the base http url.
     *
     * @return restful api base url information.
     */
    fun getApiBaseUrl(): String
}