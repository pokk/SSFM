package taiwan.no1.app.ssfm.misc.utilies.devices

import android.media.MediaDataSource
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

/**
 * For downloading and saving file to storage.
 *
 * Created by weian on 2017/8/19.
 */

class MediaDownloadModel: MediaDataSource {
    @Volatile lateinit var mediaBuffer: ByteArray
    var url: String = ""
    var percentage: Double = 0.toDouble()
    var listener: DownloadListener

    constructor(url: String, listener: DownloadListener) : super() {
        this.url = url
        this.listener = listener

        val downloadThread = Thread(Runnable {
            val conn = URL(this@MediaDownloadModel.url)
            val stream_url = URL(this@MediaDownloadModel.url)

            val connect = conn.openConnection()
            val total_size: Double = connect.contentLength.toDouble()

            val inputStream: InputStream = stream_url.openStream()
            val b = ByteArrayOutputStream()
            var read = 0

            while (read != -1) {
                read = inputStream.read()
                b.write(read)
                percentage = b.size().toDouble().div(total_size).times(100)
            }
            inputStream.close()

            b.flush()
            mediaBuffer = b.toByteArray()
            b.close()
            this@MediaDownloadModel.listener.onDownloadFinish()
        })
        downloadThread.start()
    }

    @Synchronized @Throws(IOException::class)
    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {
        var read_size = size
        synchronized(mediaBuffer) {
            val length = mediaBuffer.size
            if (position >= length) {
                return -1 // -1 indicates EOF
            }
            if (position + read_size > length) {
                read_size -= (position + read_size - length).toInt()
            }
            System.arraycopy(mediaBuffer, position.toInt(), buffer, offset, size)
            return size
        }
    }

    override fun getSize(): Long = mediaBuffer.size.toLong()

    override fun close() {}

    fun getDownloadPercentage(): Double = percentage

    fun writeToFile(path: String) {
        Thread(Runnable {
            val stream = FileOutputStream(path)
            stream.write(mediaBuffer)
            stream.close()
        }).start()
    }

    interface DownloadListener {
        fun onDownloadFinish(): Unit
    }
}
