package taiwan.no1.app.ssfm.models.entities.lastfm

/**
 * @author  jieyi
 * @since   10/17/17
 */
data class TagEntity(var tag: Tag) {
    data class Tag(var name: String?,
                   var total: Int?,
                   var reach: Int?,
                   var url: String?,
                   var wiki: Wiki?) : BaseEntity
}
