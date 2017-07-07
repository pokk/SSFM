package taiwan.no1.app.ssfm.mvvm.models

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
        var index: Int = -1

        // FIXME: too ugly
        if (this.misRandom) {
            index = (Math.random() * this.mTotal).toInt()
        } else if (this.misLoopOne) {
            index = mCurrentIndex
        } else if (this.misLoopAll) {
            index = (mCurrentIndex + 1).rem(this.mTotal)
        }

        return index
    }

    override fun setList(total: Int) {
        this.mTotal = total
    }

    override fun nowPlaying(): Int = mCurrentIndex

    override fun previous():Int {
        this.mCurrentIndex  = this.mPrevious.pop()
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