package taiwan.no1.app.ssfm.misc.extension

import java.text.DecimalFormat

/**
 * @author  jieyi
 * @since   11/5/17
 */
fun String.formatToMoneyKarma(): String = DecimalFormat("###,###").format(java.lang.Double.parseDouble(this))