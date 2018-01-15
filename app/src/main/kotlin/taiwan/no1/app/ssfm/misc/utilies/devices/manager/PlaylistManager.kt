package taiwan.no1.app.ssfm.misc.utilies.devices.manager

import java.util.Random

/**
 * @author  jieyi
 * @since   1/13/18
 */
abstract class PlaylistManager<T> {
    companion object {
        private const val ADD_OPTIONAL_FRONT = 0
        private const val ADD_OPTIONAL_BACK = 1
        private const val ADD_OPTIONAL_MIDDLE = 2
    }

    abstract val playlist: MutableList<T>
    var currentIndex = -1
    val playlistSize get() = playlist.size
    val next
        get() = (currentIndex + 1).takeIf { it >= playlistSize }?.let {
            currentIndex++
            playlist[it]
        }
    val loopingNext
        get() = (currentIndex + 1).rem(playlistSize).let {
            currentIndex = it
            playlist[it]
        }
    val again get() = playlist[currentIndex]
    val random get() = Random().nextInt(playlistSize).let(playlist::get)
    val previous
        get() = (currentIndex - 1).takeIf { 0 <= it }?.let {
            currentIndex--
            playlist[it]
        }
    val loopingPrevious get() = (currentIndex - 1).let { if (it < 0) playlistSize - it else it }.let(playlist::get)

    fun setIndex(element: T): Boolean {
        playlist.forEachIndexed { index, item ->
            if (item == element) {
                currentIndex = index
                return true
            }
        }

        currentIndex = -1
        return false
    }

    fun append(newPlaylist: List<T>) = addPlaylist(newPlaylist, ADD_OPTIONAL_BACK)

    fun enhead(newPlaylist: List<T>) = addPlaylist(newPlaylist, ADD_OPTIONAL_FRONT)

    fun add(newPlaylist: List<T>, index: Int) = addPlaylist(newPlaylist, ADD_OPTIONAL_MIDDLE, index)

    fun remove(index: Int) = if (index < playlistSize)
        playlist.removeAt(index)
    else
        throw IndexOutOfBoundsException()

    fun remove(element: T) = playlist.remove(element)

    fun clearPlaylist() = playlist.clear()

    private fun addPlaylist(newPlaylist: List<T>, optional: Int, index: Int = 0) =
        when (optional) {
            ADD_OPTIONAL_FRONT -> playlist.addAll(0, newPlaylist)
            ADD_OPTIONAL_BACK -> (playlistSize.takeIf { 0 < it } ?: 1).let { playlist.addAll(it - 1, newPlaylist) }
            ADD_OPTIONAL_MIDDLE -> playlist.addAll(index, newPlaylist)
            else -> false
        }
}
