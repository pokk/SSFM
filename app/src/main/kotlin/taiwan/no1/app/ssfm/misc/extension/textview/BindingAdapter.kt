package taiwan.no1.app.ssfm.misc.extension.textview

import android.databinding.BindingAdapter
import at.blogc.android.views.ExpandableTextView

/**
 * @author  jieyi
 * @since   10/31/17
 */
@BindingAdapter("android:toggle")
fun ExpandableTextView.toggle(expand: Boolean) = toggle()
