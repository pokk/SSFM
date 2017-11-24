@file:Suppress("NOTHING_TO_INLINE")

package taiwan.no1.app.ssfm.misc.extension

import android.support.annotation.ArrayRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import com.devrapid.kotlinknifer.getColorWithAlpha
import com.devrapid.kotlinknifer.getResColor
import com.devrapid.kotlinknifer.getResColorWithAlpha
import org.jetbrains.anko.dimen
import taiwan.no1.app.ssfm.App

/**
 * @author  jieyi
 * @since   11/22/17
 */
inline fun gContext() = App.compactContext

inline fun gDimens(@DimenRes id: Int) = gContext().dimen(id)

inline fun gStrings(@StringRes id: Int): String = gContext().getString(id)

inline fun gText(@StringRes id: Int): CharSequence = gContext().getText(id)

inline fun gStringArray(@ArrayRes id: Int): Array<out String> = gContext().resources.getStringArray(id)

inline fun getTextArray(@ArrayRes id: Int): Array<out CharSequence> = gContext().resources.getTextArray(id)

inline fun gIntArray(@ArrayRes id: Int): IntArray = gContext().resources.getIntArray(id)

inline fun gColor(@ColorRes id: Int) = gContext().getResColor(id)

inline fun gAlphaColor(@ColorRes id: Int, ratio: Float) = gContext().getResColorWithAlpha(id, ratio)

inline fun gAlphaIntColor(@ColorInt color: Int, ratio: Float) = gContext().getColorWithAlpha(color, ratio)
