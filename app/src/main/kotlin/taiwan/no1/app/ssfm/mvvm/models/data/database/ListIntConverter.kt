package taiwan.no1.app.ssfm.mvvm.models.data.database

import com.raizlabs.android.dbflow.converter.TypeConverter


/**
 *
 * @author  jieyi
 * @since   8/19/17
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
class ListIntConverter: TypeConverter<String, IntArray>() {
    companion object {
        private const val SEPARATE_SYMBOL = ", "
    }

    override fun getModelValue(data: String): IntArray =
        data.split(SEPARATE_SYMBOL).map(String::toInt).toIntArray()

    override fun getDBValue(model: IntArray): String = model.joinToString()
}