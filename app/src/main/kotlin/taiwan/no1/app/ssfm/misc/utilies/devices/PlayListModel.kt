package taiwan.no1.app.ssfm.misc.utilies.devices

import java.util.Stack

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
    private var mState: IPlayList.EMusicState = IPlayList.EMusicState.EMusicState_Normal

    private fun getNextIndex(): Int {
        val maps = mapOf(
            Pair({ IPlayList.EMusicState.EMusicState_Random }, (Math.random() * this.mTotal).toInt()),
            Pair({ IPlayList.EMusicState.EMusicState_LoopOne }, this.mCurrentIndex),
            Pair({ IPlayList.EMusicState.EMusicState_LoopAll }, (this.mCurrentIndex + 1).rem(this.mTotal)),
            Pair({ IPlayList.EMusicState.EMusicState_Normal }, if (
                    this.mCurrentIndex.inc().equals(this.mTotal)
                    || this.mCurrentIndex.equals(-1))
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
            this.mState = IPlayList.EMusicState.EMusicState_Random
        else
            this.mState = IPlayList.EMusicState.EMusicState_Normal
    }

    override fun loopOne(is_loop: Boolean) {
        this.misLoopOne = is_loop
        if (is_loop)
            this.mState = IPlayList.EMusicState.EMusicState_LoopOne
        else
            this.mState = IPlayList.EMusicState.EMusicState_Normal
    }

    override fun loopAll(is_loop: Boolean) {
        this.misLoopAll = is_loop
        if (is_loop)
            this.mState = IPlayList.EMusicState.EMusicState_LoopAll
        else
            this.mState = IPlayList.EMusicState.EMusicState_Normal
    }

    override fun getState(): IPlayList.EMusicState = this.mState
}