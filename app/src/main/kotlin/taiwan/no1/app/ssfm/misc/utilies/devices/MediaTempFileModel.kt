package taiwan.no1.app.ssfm.misc.utilies.devices

import android.nfc.Tag
import android.util.Log
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.misc.utilies.devices.IMediaDownloader.DownloadListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.concurrent.thread

/**
 * Created by weian on 2017/10/21.
 */

class MediaTempFileModel(url: String, listener: DownloadListener): IMediaDownloader {
    private var url: String = ""
    private var listener: DownloadListener
    @Volatile lateinit var mediaBuffer: ByteArray
    var percentage: Double = .0
        private set

    companion object {
        val TEMP = "/storage/emulated/0/Download/temp.mp3"
    }

    init {
        this.url = url
        this.listener = listener
    }

    override fun start() {
        thread {

            val file = File(TEMP)
            file.takeIf { it.exists() }.let {
                file.createNewFile()
            }

            val conn = URL(this@MediaTempFileModel.url)
            val streamUrl = URL(this@MediaTempFileModel.url)

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

            writeToTemp()
            this@MediaTempFileModel.listener.onDownloadFinish()
        }
    }

    fun writeToTemp() {
        thread {
            val stream = FileOutputStream(TEMP)
            stream.write(mediaBuffer)
            stream.close()
        }
    }

    override fun writeToFile(filePath: String): Int {
        val tempFile = File(TEMP)
        if (!tempFile.exists()) {
            loge("temp($TEMP) is not exist")
            return 0
        }

        val newFile = File(filePath)

        if (newFile.exists() && newFile.length().toInt() != 0) {
            logw("$filePath is already exist")
            return newFile.length().toInt()
        }

        tempFile.renameTo(newFile)

        return newFile.length().toInt()
    }
}