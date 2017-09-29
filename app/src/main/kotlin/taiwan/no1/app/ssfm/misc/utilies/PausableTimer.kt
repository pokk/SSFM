package taiwan.no1.app.ssfm.misc.utilies

import android.os.CountDownTimer

/**
 * Countdown timer with the pause function.
 *
 * @author  jieyi
 * @since   7/23/17
 */
class PausableTimer(private val millisInFuture: Long = -1, private val countDownInterval: Long = 1000) {
    var onTick: (millisUntilFinished: Long) -> Unit = {}
    var onFinish: () -> Unit = {}
    var isPause = false
    var isStart = false
    var curTime = 0L
    lateinit private var timer: CountDownTimer

    init {
        val millisTime = if (-1L == millisInFuture) Long.MAX_VALUE else millisInFuture
        init(millisTime, countDownInterval)
    }

    fun pause(): Long {
        isPause = true
        stop()

        return curTime
    }

    fun resume() {
        val time = if (isPause && 0 <= curTime) {
            isPause = false
            curTime
        }
        else {
            millisInFuture
        }

        stop()
        init(time, countDownInterval)
        start()
    }

    fun start() {
        if (!isStart && !isPause) {
            if (0L == curTime) {
                init(millisInFuture, countDownInterval)
            }
            timer.start()
            isStart = true
        }
        else if (isPause) {
            resume()
        }
    }

    fun stop() {
        timer.cancel()
        isStart = false
    }

    private fun init(millisInFuture: Long, countDownInterval: Long) {
        timer = object: CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                // OPTIMIZE(jieyi): 9/29/17
                // val time = (Math.round(millisUntilFinished.toDouble() / 1000) - 1).toInt()
                this@PausableTimer.curTime = millisUntilFinished
                this@PausableTimer.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                this@PausableTimer.curTime = 0
                this@PausableTimer.onFinish()
            }
        }
    }
}