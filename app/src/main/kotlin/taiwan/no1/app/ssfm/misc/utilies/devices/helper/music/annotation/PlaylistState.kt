package taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.annotation

import android.support.annotation.IntDef
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.constants.MusicStateConstant

/**
 * @author  jieyi
 * @since   2017/12/02
 */
@IntDef(MusicStateConstant.PLAYLIST_STATE_LOOP_ALL,
        MusicStateConstant.PLAYLIST_STATE_LOOP_ONE,
        MusicStateConstant.PLAYLIST_STATE_NORMAL,
        MusicStateConstant.PLAYLIST_STATE_RANDOM,
        MusicStateConstant.PLAYLIST_STATE_UNKNOWN)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD,
        AnnotationTarget.TYPE,
        AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class PlaylistState
