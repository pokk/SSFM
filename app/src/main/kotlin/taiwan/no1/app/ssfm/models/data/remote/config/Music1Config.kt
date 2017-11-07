package taiwan.no1.app.ssfm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   5/21/17
 */

class Music1Config: IApiConfig {
    companion object {
        const val API_REQUEST = "search/song"
        // All basic http api url of KoGou Search Music.
        private const val BASE_URL = "http://mobilecdn.kugou.com/api/v3/"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}