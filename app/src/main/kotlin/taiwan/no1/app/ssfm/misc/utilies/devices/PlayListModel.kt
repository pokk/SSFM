package taiwan.no1.app.ssfm.misc.utilies.devices

import java.util.Stack
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.RANDOM
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.LOOPONE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.LOOPALL

/**
 * Controlling play list.
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
    private var misNormal: Boolean = false
    private var mState: EMUSICSTATE = NORMAL

    private fun getNextIndex(): Int {
        val maps = mapOf(
            Pair({ RANDOM }, (Math.random() * this.mTotal).toInt()),
            Pair({ LOOPONE }, this.mCurrentIndex),
            Pair({ LOOPALL }, (this.mCurrentIndex + 1).rem(this.mTotal)),
            Pair({ NORMAL }, if (
                    this.mCurrentIndex.inc()== this.mTotal
                    || this.mCurrentIndex == -1)
                    -1 else this.mCurrentIndex.inc()))

        return run {
            maps.forEach { (c, r) -> if (c() == this.mState) return@run r }
            // else
            -1
        }
    }

    override fun setList(total: Int) {
        this.mTotal = total
    }

    override fun nowPlaying(): Int = mCurrentIndex

    override fun play(index: Int) {
        this.mPrevious.push(this.mCurrentIndex)
        this.mCurrentIndex = index
    }

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
        this.misNormal = !is_random
        if (is_random)
            this.mState = RANDOM
        else
            this.mState = NORMAL
    }

    override fun loopOne(is_loop: Boolean) {
        this.misLoopOne = is_loop
        if (is_loop)
            this.mState = LOOPONE
        else
            this.mState = NORMAL
    }

    override fun loopAll(is_loop: Boolean) {
        this.misLoopAll = is_loop
        if (is_loop)
            this.mState = LOOPALL
        else
            this.mState = NORMAL
    }

    override fun getState(): EMUSICSTATE = this.mState
}