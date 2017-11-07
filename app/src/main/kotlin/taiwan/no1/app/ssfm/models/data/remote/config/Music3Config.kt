package taiwan.no1.app.ssfm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   5/21/17
 */
class Music3Config: IApiConfig {
    companion object {
        const val API_REQUEST = "2.0/"
        // All basic http api url of KoGou Detail Music.
        private const val BASE_URL = "http://ws.audioscrobbler.com/"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}