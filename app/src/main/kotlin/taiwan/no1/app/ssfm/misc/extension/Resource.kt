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
fun gContext() = App.appComponent.context()

fun gDimens(@DimenRes id: Int) = gContext().dimen(id)

fun gStrings(@StringRes id: Int) = gContext().getString(id)

fun gText(@StringRes id: Int) = gContext().getText(id)

fun gStringArray(@ArrayRes id: Int) = gContext().resources.getStringArray(id)

fun gColor(@ColorRes id: Int) = gContext().getResColor(id)

fun gAlphaColor(@ColorRes id: Int, ratio: Float) = gContext().getResColorWithAlpha(id, ratio)

fun gAlphaIntColor(@ColorInt color: Int, ratio: Float) = gContext().getColorWithAlpha(color, ratio)
