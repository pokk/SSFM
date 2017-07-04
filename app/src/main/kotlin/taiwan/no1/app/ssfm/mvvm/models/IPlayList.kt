package taiwan.no1.app.ssfm.mvvm.models

/**
 * for handling music list.
 *
 * Created by weian on 2017/6/18.
 */
interface IPlayList {
    fun setList()
    fun nowPlaying(): Int
    fun previous()
    fun next()
}
