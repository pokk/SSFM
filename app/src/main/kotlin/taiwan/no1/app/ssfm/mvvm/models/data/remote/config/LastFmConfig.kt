package taiwan.no1.app.ssfm.mvvm.models.data.remote.config

/**
 * Last Fm api configuration of the uri, the token, etc.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LastFmConfig: IApiConfig {
    companion object {
        // All basic http api url of Last Fm. 
        private val BASE_URL = "http://ws.audioscrobbler.com/2.0/"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}