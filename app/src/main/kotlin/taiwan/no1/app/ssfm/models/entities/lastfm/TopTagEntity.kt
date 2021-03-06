package taiwan.no1.app.ssfm.models.entities.lastfm

import com.google.gson.annotations.SerializedName

/**
 * @author  jieyi
 * @since   10/16/17
 */
data class TopTagEntity(@SerializedName("tags") var tag: Tags) {
    data class Tags(@SerializedName("tag") var tags: List<TagEntity.Tag>,
                    @SerializedName("@attr") var attr: Attr?)
}
