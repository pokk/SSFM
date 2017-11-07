package taiwan.no1.app.ssfm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   5/21/17
 */

class Music2Config: IApiConfig {
    companion object {
        const val API_REQUEST = "yy/index.php"
        // All basic http api url of KoGou Detail Music.
        private const val BASE_URL = "http://www.kugou.com/"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}