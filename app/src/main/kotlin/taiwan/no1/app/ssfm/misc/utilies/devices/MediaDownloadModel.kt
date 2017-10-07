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

class MediaDownloadModel: MediaDataSource {
    @Volatile lateinit var mediaBuffer: ByteArray
    var url: String = ""
    var percentage: Double = 0.toDouble()
    var listener: DownloadListener

    constructor(url: String, listener: DownloadListener) {
        this.url = url
        this.listener = listener

        thread {
            val conn = URL(this@MediaDownloadModel.url)
            val stream_url = URL(this@MediaDownloadModel.url)

            val connect = conn.openConnection()
            val total_size = connect.contentLength.toDouble()

            stream_url.openStream().use {
                val stream = it
                ByteArrayOutputStream().use {
                    var read = 0
                    while (read != -1) {
                        read = stream.read()
                        it.write(read)
                        percentage = it.size().toDouble().div(total_size).times(100)
                    }
                    it.flush()
                    mediaBuffer = it.toByteArray()
                }

            }

            this@MediaDownloadModel.listener.onDownloadFinish()
        }
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
            System.arraycopy(mediaBuffer, position.toInt(), buffer, offset, read_size)
            return size
        }
    }

    override fun getSize(): Long = mediaBuffer.size.toLong()

    override fun close() {}

    fun getDownloadPercentage(): Double = percentage

    fun writeToFile(path: String) {
        thread {
            val stream = FileOutputStream(path)
            stream.write(mediaBuffer)
            stream.close()
        }.start()
    }

    interface DownloadListener {
        fun onDownloadFinish(): Unit
    }
}
