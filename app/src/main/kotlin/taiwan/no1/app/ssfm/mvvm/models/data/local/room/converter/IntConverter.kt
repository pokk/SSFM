package taiwan.no1.app.ssfm.mvvm.models.data.local.room.converter

import android.arch.persistence.room.TypeConverter

/**
 *
 * @author  jieyi
 * @since   8/17/17
 */
class IntConverter {
    @TypeConverter
    open fun listToInt(array: List<Int>): String {
        if (array.isEmpty()) {
            return ""
        }

        return array.joinToString()
    }

    @TypeConverter
    open fun intToList(value: String): List<Int> =
        if (value.isNotEmpty()) value.split(", ").map(String::toInt) else emptyList()
}