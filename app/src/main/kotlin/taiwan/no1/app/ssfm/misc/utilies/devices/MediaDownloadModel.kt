package taiwan.no1.app.ssfm.misc.utilies.devices

import android.media.MediaDataSource

/**
 * For downloading and saving file to storage.
 *
 * Created by weian on 2017/8/19.
 */

class MediaDownloadModel: MediaDataSource {
    @Volatile lateinit var mediaBuffer: ByteArray
    var url: String = ""

    constructor() : super()


    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSize(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
