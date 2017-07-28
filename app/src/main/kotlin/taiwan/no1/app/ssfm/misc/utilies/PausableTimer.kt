package taiwan.no1.app.ssfm.misc.utilies

import android.os.CountDownTimer

/**
 *
 * @author  jieyi
 * @since   7/23/17
 */
class PausableTimer(val millisInFuture: Long = -1, val countDownInterval: Long = 1000) {
    var isPause = false
    var isStart = false
    var curTime = 0L
    lateinit var timer: CountDownTimer
    var ontick: (millisUntilFinished: Long) -> Unit = {}
    var onfinish: () -> Unit = {}

    init {
        val millisTime = if (-1L == this.millisInFuture) Long.MAX_VALUE else this.millisInFuture
        this.init(millisTime, this.countDownInterval)
    }

    fun onTick(block: (millisUntilFinished: Long) -> Unit) {
        ontick = block
    }

    fun onFinish(block: () -> Unit) {
        this.onfinish = block
    }

    fun pause(): Long {
        this.isPause = true
        this.stop()

        return this.curTime
    }

    fun resume() {
        val time = if (this.isPause && 0 <= this.curTime) {
            this.isPause = false
            this.curTime
        }
        else {
            this.millisInFuture
        }

        this.stop()
        this.init(time, this.countDownInterval)
        this.start()
    }

    fun start() {
        if (!this.isStart && !this.isPause) {
            if (0L == this.curTime) {
                this.init(this.millisInFuture, this.countDownInterval)
            }
            this.timer.start()
            this.isStart = true
        }
        else if (this.isPause) {
            this.resume()
        }
    }

    fun stop() {
        this.timer.cancel()
        this.isStart = false
    }

    private fun init(millisInFuture: Long, countDownInterval: Long) {
        this.timer = object: CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                this@PausableTimer.curTime = millisUntilFinished
                this@PausableTimer.ontick(millisUntilFinished)
            }

            override fun onFinish() {
                this@PausableTimer.curTime = 0
                this@PausableTimer.onfinish()
            }
        }
    }
}