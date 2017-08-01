package taiwan.no1.app.ssfm.misc.utilies.devices

import java.util.*

/**
 * for controlling play list
 *
 * Created by weian on 2017/7/6.
 */

class PlayListModel: IPlayList {
    private var mTotal: Int = 0
    private var mCurrentIndex: Int = -1
    private var mPrevious: Stack<Int> = Stack()
    private var misRandom: Boolean = false
    private var misLoopOne: Boolean = false
    private var misLoopAll: Boolean = false

    private fun getNextIndex(): Int {
        val maps = mapOf(
            Pair({ this.misRandom }, (Math.random() * this.mTotal).toInt()),
            Pair({ this.misLoopOne }, this.mCurrentIndex),
            Pair({ this.misLoopAll }, (this.mCurrentIndex + 1).rem(this.mTotal)))

        return run {
            maps.forEach { (c, r) -> if (c()) return@run r }
            // else
            -1
        }
    }

    override fun setList(total: Int) {
        this.mTotal = total
    }

    override fun nowPlaying(): Int = mCurrentIndex

    override fun previous(): Int {
        this.mCurrentIndex = this.mPrevious.pop()

        return this.mCurrentIndex
    }

    override fun next(): Int {
        this.mPrevious.push(this.mCurrentIndex)
        this.mCurrentIndex = this.getNextIndex()

        return this.mCurrentIndex
    }

    override fun random(is_random: Boolean) {
        this.misRandom = is_random
    }

    override fun loopOne(is_loop: Boolean) {
        this.misLoopOne = is_loop
    }

    override fun loopAll(is_loop: Boolean) {
        this.misLoopAll = is_loop
    }

    override fun isRandom(): Boolean = this.misRandom
}