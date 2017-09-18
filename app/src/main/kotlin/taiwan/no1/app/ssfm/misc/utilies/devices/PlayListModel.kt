package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.LOOPALL
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.LOOPONE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMUSICSTATE.RANDOM
import java.util.*

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
            Pair({ RANDOM }, (Math.random() * mTotal).toInt()),
            Pair({ LOOPONE }, mCurrentIndex),
            Pair({ LOOPALL }, (mCurrentIndex + 1).rem(mTotal)),
            Pair({ NORMAL }, if (
            mCurrentIndex.inc() == mTotal
                || mCurrentIndex == -1)
                -1
            else mCurrentIndex.inc()))

        return run {
            maps.forEach { (c, r) -> if (c() == mState) return@run r }
            // else
            -1
        }
    }

    override fun setList(total: Int) {
        mTotal = total
    }

    override fun nowPlaying(): Int = mCurrentIndex

    override fun play(index: Int) {
        mPrevious.push(mCurrentIndex)
        mCurrentIndex = index
    }

    override fun previous(): Int {
        mCurrentIndex = mPrevious.pop()

        return mCurrentIndex
    }

    override fun next(): Int {
        mPrevious.push(mCurrentIndex)
        mCurrentIndex = getNextIndex()

        return mCurrentIndex
    }

    override fun random(is_random: Boolean) {
        misRandom = is_random
        misNormal = !is_random
        if (is_random)
            mState = RANDOM
        else
            mState = NORMAL
    }

    override fun loopOne(is_loop: Boolean) {
        misLoopOne = is_loop
        if (is_loop)
            mState = LOOPONE
        else
            mState = NORMAL
    }

    override fun loopAll(is_loop: Boolean) {
        misLoopAll = is_loop
        if (is_loop)
            mState = LOOPALL
        else
            mState = NORMAL
    }

    override fun getState(): EMUSICSTATE = mState
}