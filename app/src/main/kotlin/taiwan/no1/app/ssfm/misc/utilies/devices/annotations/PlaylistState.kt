package taiwan.no1.app.ssfm.misc.utilies.devices.annotations

import android.support.annotation.IntDef
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant

/**
 * @author  jieyi
 * @since   2017/12/02
 */
@IntDef(MusicStateConstant.PLAYLIST_STATE_UNKNOWN,
    MusicStateConstant.PLAYLIST_STATE_NORMAL,
    MusicStateConstant.PLAYLIST_STATE_RANDOM,
    MusicStateConstant.PLAYLIST_STATE_LOOP_ONE,
    MusicStateConstant.PLAYLIST_STATE_LOOP_ALL)
@Retention(AnnotationRetention.SOURCE)
//@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
annotation class PlaylistState