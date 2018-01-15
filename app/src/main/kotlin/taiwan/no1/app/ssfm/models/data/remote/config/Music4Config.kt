package taiwan.no1.app.ssfm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   11/20/17
 */
class Music4Config : IApiConfig {
    companion object {
        const val API_REQUEST = "/api"
        // All basic http api url of KoGou Detail Music.
        private const val BASE_URL = "http://www.musicfmjp.com"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}