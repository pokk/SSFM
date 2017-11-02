package taiwan.no1.app.ssfm.misc.extension.customview

import android.databinding.BindingAdapter
import me.gujun.android.taggroup.TagGroup

/**
 * @author  jieyi
 * @since   11/3/17
 */
@BindingAdapter("android:tags")
fun TagGroup.setSrc(tags: ArrayList<String>) = setTags(tags)