package taiwan.no1.app.ssfm.misc.utilies.devices

import android.media.MediaDataSource
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread

/**
 * For downloading and saving file to storage.
 *
 * Created by weian on 2017/8/19.
 */

class MediaDataSourceModel(private var url: String, private var listener: IMediaDownloader.DownloadListener): MediaDataSource(), IMediaDownloader {
    @Volatile lateinit var mediaBuffer: ByteArray
    var percentage: Double = .0
        private set

    @Synchronized
    @Throws(IOException::class)
    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {
        var readSize = size
        synchronized(mediaBuffer) {
            val length = mediaBuffer.size
            if (position >= length) {
                return -1 // -1 indicates EOF
            }
            if (position + readSize > length) {
                readSize -= (position + readSize - length).toInt()
            }
            System.arraycopy(mediaBuffer, position.toInt(), buffer, offset, readSize)
            return size
        }
    }

    override fun getSize(): Long = mediaBuffer.size.toLong()

    override fun close() {}

    override fun start() {
        thread {
            val conn = URL(this@MediaDataSourceModel.url)
            val streamUrl = URL(this@MediaDataSourceModel.url)

            val connect = conn.openConnection()
            val totalSize = connect.contentLength.toDouble()

            streamUrl.openStream().use {
                val stream = it
                ByteArrayOutputStream().use {
                    var read = 0
                    while (read != -1) {
                        read = stream.read()
                        it.write(read)
                        percentage = it.size().toDouble().div(totalSize).times(100)
                    }
                    it.flush()
                    mediaBuffer = it.toByteArray()
                }
            }

            this@MediaDataSourceModel.listener.onDownloadFinish()
        }
    }

    override fun writeToFile(path: String): Int {
        thread {
            val stream = FileOutputStream(path)
            stream.write(mediaBuffer)
            stream.close()
        }

        return mediaBuffer.size
    }
}
