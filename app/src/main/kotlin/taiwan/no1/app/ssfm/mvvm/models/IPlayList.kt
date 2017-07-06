package taiwan.no1.app.ssfm.mvvm.models

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
}
