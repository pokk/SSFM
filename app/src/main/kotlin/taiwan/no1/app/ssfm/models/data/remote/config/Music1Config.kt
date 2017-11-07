package taiwan.no1.app.ssfm.models.data.remote.config

/**
 *
 * @author  jieyi
 * @since   5/21/17
 */
class Music1Config: IApiConfig {
    companion object {
        const val API_REQUEST = "track.getInfo"
        // All basic http api url of Search Music.
        private const val BASE_URL = "lastfm://artist/cher/fans"
    }

    override fun getApiBaseUrl(): String = BASE_URL
}