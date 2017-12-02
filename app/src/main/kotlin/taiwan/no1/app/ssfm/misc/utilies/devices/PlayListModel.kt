package taiwan.no1.app.ssfm.misc.utilies.devices

import android.support.annotation.IntRange
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_LOOP_ALL
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_LOOP_ONE
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_NORMAL
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicStateConstant.PLAYLIST_STATE_RANDOM
import java.util.Stack

/**
 * Controlling play list.
 *
 * Created by weian on 2017/7/6.
 */

class PlayListModel : IPlayList {
    private var mTotal: Int = 0
    private var mCurrentIndex: Int = -1
    private var mPrevious: Stack<Int> = Stack()
    private var mIsRandom: Boolean = false
    private var mIsLoopOne: Boolean = false
    private var mIsLoopAll: Boolean = false
    private var mIsNormal: Boolean = false
    @PlaylistState var mState = PLAYLIST_STATE_NORMAL

    private fun getNextIndex(): Int {
        val maps = mapOf(
            Pair({ PLAYLIST_STATE_RANDOM }, (Math.random() * mTotal).toInt()),
            Pair({ PLAYLIST_STATE_LOOP_ONE }, mCurrentIndex),
            Pair({ PLAYLIST_STATE_LOOP_ALL }, (mCurrentIndex + 1).rem(mTotal)),
            Pair({ PLAYLIST_STATE_NORMAL },
                if (mCurrentIndex.inc() == mTotal || mCurrentIndex == -1) -1 else mCurrentIndex.inc()))

        return run {
            maps.forEach { (c, r) -> if (c() == mState) return@run r }
            // else
            -1
        }
    }

    override fun setList(@IntRange total: Int) {
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
        mState = if (is_random) PLAYLIST_STATE_RANDOM else PLAYLIST_STATE_NORMAL
    }

    override fun loopOne(is_loop: Boolean) {
        mIsLoopOne = is_loop
        mState = if (is_loop) PLAYLIST_STATE_LOOP_ONE else PLAYLIST_STATE_NORMAL
    }

    override fun loopAll(is_loop: Boolean) {
        mIsLoopAll = is_loop
        mState = if (is_loop) PLAYLIST_STATE_LOOP_ALL else PLAYLIST_STATE_NORMAL
    }

    override fun getState(): Long = mState
}