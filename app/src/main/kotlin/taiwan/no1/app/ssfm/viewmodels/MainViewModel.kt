package taiwan.no1.app.ssfm.viewmodels

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.AppLog
import taiwan.no1.app.ssfm.models.TestModel

/**
 *
 * @author  jieyi
 * @since   5/8/17
 */

class MainViewModel(mContext: Context): BaseViewModel(mContext) {
    var title: ObservableField<String> = ObservableField()

    init {
        AppLog.w()
        val model: TestModel = TestModel("Jieyi", 20)
        this.title.set(model.name)
    }

    fun itemClick(view: View) {
        AppLog.w("Hello")
    }
}
