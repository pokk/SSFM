package taiwan.no1.app.ssfm.misc.utilies.devices

import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.LOOPALL
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.LOOPONE
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList.EMusicState.RANDOM
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
    private var mIsRandom: Boolean = false
    private var mIsLoopOne: Boolean = false
    private var mIsLoopAll: Boolean = false
    private var mIsNormal: Boolean = false
    private var mState: EMusicState = NORMAL

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
        mIsRandom = is_random
        mIsNormal = !is_random
        if (is_random)
            mState = RANDOM
        else
            mState = NORMAL
    }

    override fun loopOne(is_loop: Boolean) {
        mIsLoopOne = is_loop
        if (is_loop)
            mState = LOOPONE
        else
            mState = NORMAL
    }

    override fun loopAll(is_loop: Boolean) {
        mIsLoopAll = is_loop
        if (is_loop)
            mState = LOOPALL
        else
            mState = NORMAL
    }

    override fun getState(): EMusicState = mState
}