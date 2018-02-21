package taiwan.no1.app.ssfm.misc.extension.customview

import android.databinding.BindingAdapter
import com.github.mmin18.widget.RealtimeBlurView
import me.gujun.android.taggroup.TagGroup

/**
 * @author  jieyi
 * @since   11/3/17
 */
@BindingAdapter("android:tags")
fun TagGroup.setSrc(tags: ArrayList<String>) = setTags(tags)

@BindingAdapter("android:blurBackground")
fun RealtimeBlurView.setBackground(color: Int) = setOverlayColor(color)

//@BindingAdapter("android:onClickListener",
//                "android:startTime",
//                "android:endTime",
//                "android:imageUri",
//                requireAll = false)
//fun RealtimeBlurView.setEventCallback(color: Int) {
//}
