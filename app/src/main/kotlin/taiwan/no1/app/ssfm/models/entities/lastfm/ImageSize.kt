package taiwan.no1.app.ssfm.models.entities.lastfm

import android.support.annotation.IntDef
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.MEDIUM
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.SMALL

/**
 * @author  jieyi
 * @since   10/17/17
 */
@IntDef(SMALL.toLong(), MEDIUM.toLong(), LARGE.toLong(), EXTRA_LARGE.toLong())
@kotlin.annotation.Retention
internal annotation class ImageSize