package taiwan.no1.app.ssfm.misc.extension

import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity

/**
 * @author  jieyi
 * @since   2018/01/20
 */
fun Collection<PlaylistItemEntity>.copy(): MutableList<PlaylistItemEntity> =
    ArrayList<PlaylistItemEntity>(size).also { dup -> forEach { dup.add(it.copy()) } }