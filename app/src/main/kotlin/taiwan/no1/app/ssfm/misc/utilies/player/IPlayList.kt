package taiwan.no1.app.ssfm.misc.utilies.player

/**
 * for handling music list.
 *
 * Created by weian on 2017/6/18.
 */
interface IPlayList {
    fun setList(total: Int)
    fun nowPlaying(): Int
    fun previous(): Int
    fun next(): Int
    fun loopOne(is_loop: Boolean)
    fun loopAll(is_loop: Boolean)
    fun random(is_random: Boolean)
    fun isRandom(): Boolean
}
