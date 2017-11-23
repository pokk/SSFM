package taiwan.no1.app.ssfm.misc.extension

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * @author  jieyi
 * @since   11/23/17
 */
fun <T> glideListener(listener: GlideRequestListenerWrapper<T>.() -> Unit): RequestListener<T> {
    val wrapper = GlideRequestListenerWrapper<T>().apply(listener)

    return object : RequestListener<T> {
        override fun onLoadFailed(e: GlideException?,
                                  model: Any?,
                                  target: Target<T>?,
                                  isFirstResource: Boolean) =
            wrapper.onLoadFailed?.invoke(e, model, target, isFirstResource) ?: false

        override fun onResourceReady(resource: T,
                                     model: Any?,
                                     target: Target<T>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean) =
            wrapper.onResourceReady?.invoke(resource, model, target, dataSource, isFirstResource) ?: false
    }
}

class GlideRequestListenerWrapper<T> {
    /**
     * Called when a load completes successfully, immediately before [Target.onResourceReady].
     *
     * @param resource          The resource that was loaded for the target.
     * @param model             The specific model that was used to load the image.
     * @param target            The target the model was loaded into.
     * @param dataSource        The [DataSource] the resource was loaded from.
     * @param isFirstResource   [true] if this is the first resource to in this load to be
     *                          loaded into the target. For example when loading a thumbnail and a
     *                          full-sized image, this will be [true] for the first image to
     *                          load and [false] for the second.
     * @return [true] if the listener has handled setting the resource on the target,
     *         [false] to allow Glide's request to update the target.
     *         Setting the resource includes handling animations, be sure to take that into account.
     */
    var onResourceReady: ((resource: T,
                           model: Any?,
                           target: Target<T>?,
                           dataSource: DataSource?,
                           isFirstResource: Boolean) -> Boolean)? = null
    /**
     * Called when an exception occurs during a load, immediately before [Target.onLoadFailed].
     * Will only be called if we currently want to display an image for the given model
     * in the given target. It is recommended to create a single instance
     * per activity/fragment rather than instantiate a new object for each call to [Glide.load]
     * to avoid object churn.
     *
     * <p> It is safe to reload this or a different model or change what is displayed in the target at
     * this point. For example:
     * <pre>
     * {@code
     * public void onLoadFailed(Exception e, T model, Target target, boolean isFirstResource) {
     *     target.setPlaceholder(R.drawable.a_specific_error_for_my_exception);
     *     Glide.load(model).into(target);
     * }
     * }
     * </pre>
     * </p>
     *
     * <p> Note - if you want to reload this or any other model after an exception, you will need to
     * include all relevant builder calls (like centerCrop, placeholder etc).
     *
     * @param e               The maybe [null] exception containing information about why the
     *                        request failed.
     * @param model           The model we were trying to load when the exception occurred.
     * @param target          The [Target] we were trying to load the image into.
     * @param isFirstResource [true] if this exception is for the first resource to load.
     * @return [true] if the listener has handled updating the target for the given exception,
     *         [false] to allow Glide's request to update the target.
     */
    var onLoadFailed: ((e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean) -> Boolean)? = null
}
