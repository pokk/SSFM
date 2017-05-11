package taiwan.no1.app.ssfm.misc.utilies.SharedPreferences

import android.content.SharedPreferences
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * @author  jieyi
 * @since   5/12/17
 */
class SharedPrefs<T>(var defaultValue: T, var onChange: (() -> Unit)? = null): ReadWriteProperty<Any, T> {
    companion object {
        var prefs: SharedPreferences by Delegates.notNull()
        fun setPrefSettings(pref: SharedPreferences) {
            prefs = pref
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val name: String = property.name

        when (this.defaultValue) {
            is Boolean -> return prefs.getBoolean(name, defaultValue as Boolean) as T
            is Float -> return prefs.getFloat(name, defaultValue as Float) as T
            is Int -> return prefs.getInt(name, defaultValue as Int) as T
            is Long -> return prefs.getLong(name, defaultValue as Long) as T
            is String -> return prefs.getString(name, defaultValue as String) as T
            is Set<*> -> return prefs.getStringSet(name, defaultValue as Set<String>) as T
            else -> throw UnsupportedOperationException("Unsupported preference type ${property.javaClass} on property $name")
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val name: String = property.name

        prefs.edit().also {
            when (this.defaultValue) {
                is Boolean -> it.putBoolean(name, value as Boolean)
                is Float -> it.putFloat(name, value as Float)
                is Int -> it.putInt(name, value as Int)
                is Long -> it.putLong(name, value as Long)
                is String -> it.putString(name, value as String)
                is Set<*> -> it.putStringSet(name, value as Set<String>)
                else -> throw UnsupportedOperationException("Unsupported preference type ${property.javaClass} on property $name")
            }
            this.onChange?.invoke()
        }.apply()
    }
}