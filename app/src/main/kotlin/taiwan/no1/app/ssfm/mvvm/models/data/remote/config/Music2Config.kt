package taiwan.no1.app.ssfm.mvvm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   5/21/17
 */

class Music2Config: IApiConfig {
    companion object {
        const val API_REQUEST = "track.scrobble"
        // All basic http api url of KoGou Detail Music.
        private const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}