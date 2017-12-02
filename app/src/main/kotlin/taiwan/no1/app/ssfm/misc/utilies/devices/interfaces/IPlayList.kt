package taiwan.no1.app.ssfm.misc.utilies.devices.interfaces

import taiwan.no1.app.ssfm.misc.utilies.devices.annotations.PlaylistState

/**
 * For handling music list.
 *
 * Created by weian on 2017/6/18.
 */
interface IPlayList {
    fun setList(total: Int)
    fun nowPlaying(): Int
    fun play(index: Int)
    fun previous(): Int
    fun next(): Int
    fun loopOne(is_loop: Boolean)
    fun loopAll(is_loop: Boolean)
    fun random(is_random: Boolean)
    @PlaylistState fun getState(): Long
}
