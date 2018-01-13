package taiwan.no1.app.ssfm.misc.utilies.devices

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
    var currentIndex: Int = 0
    val playlistSize get() = playlist.size
    val next
        get() = (currentIndex + 1).takeIf { it >= playlistSize }?.let {
            currentIndex++
            playlist[it]
        }
    val previous
        get() = (currentIndex - 1).takeIf { 0 <= it }?.let {
            currentIndex--
            playlist[it]
        }

    fun append(newPlaylist: List<T>) = addPlaylist(newPlaylist, ADD_OPTIONAL_BACK)

    fun enhead(newPlaylist: List<T>) = addPlaylist(newPlaylist, ADD_OPTIONAL_FRONT)

    fun add(newPlaylist: List<T>, index: Int) = addPlaylist(newPlaylist, ADD_OPTIONAL_MIDDLE, index)

    fun clearPlaylist() = playlist.clear()

    private fun addPlaylist(newPlaylist: List<T>, optional: Int, index: Int = 0) =
        when (optional) {
            ADD_OPTIONAL_FRONT -> playlist.addAll(0, newPlaylist)
            ADD_OPTIONAL_BACK -> playlist.addAll(playlist.size - 1, newPlaylist)
            ADD_OPTIONAL_MIDDLE -> playlist.addAll(index, newPlaylist)
            else -> false
        }
}
