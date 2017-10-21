package taiwan.no1.app.ssfm.misc.utilies.devices

/**
 * Created by weian on 2017/10/21.
 *
 */

interface IMediaDownloader {

    /**
     * start downloading media file
     */
    fun start()

    /**
     * save media file to a specified place.
     * Media file would be flush after playing completely if this function
     * doesn't be used.
     */
    fun writeToFile(filePath: String): Int

    /**
     * a callback function, which will notify caller while media file is ready.
     */
    interface DownloadListener {
        fun onDownloadFinish()
    }
}