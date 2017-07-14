package com.example.jieyi.test

/**
 *
 * @author  jieyi
 * @since   7/13/17
 */
class TimeUtils {
    companion object {
        fun number2String(mins: Int): String {
            val hour = mins / 60
            val min = mins % 60

            return "${hour.format(2)}:${min.format(2)}"
        }

        fun Int.format(digits: Int) = String.format("%0${digits}d", this)
    }
}