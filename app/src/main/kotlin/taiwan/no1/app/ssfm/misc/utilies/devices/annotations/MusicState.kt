package taiwan.no1.app.ssfm.misc.utilies.devices.annotations

import android.support.annotation.IntDef
import taiwan.no1.app.ssfm.misc.utilies.devices.constants.MusicStateConstant

/**
 * @author  jieyi
 * @since   2017/12/02
 */
@IntDef(MusicStateConstant.MUSIC_STATE_UNKNOWN,
    MusicStateConstant.MUSIC_STATE_PLAY,
    MusicStateConstant.MUSIC_STATE_PAUSE,
    MusicStateConstant.MUSIC_STATE_STOP)
@Retention(AnnotationRetention.SOURCE)
//@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
annotation class MusicState