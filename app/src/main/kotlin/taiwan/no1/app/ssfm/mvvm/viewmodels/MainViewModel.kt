package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.AppLog
import taiwan.no1.app.ssfm.mvvm.models.TestModel

/**
 *
 * @author  jieyi
 * @since   5/8/17
 */

class MainViewModel(mContext: Context): BaseViewModel(mContext) {
    private val model: TestModel = TestModel("Jieyi", 20)

    var test: ObservableField<TestModel> = ObservableField()

    init {
        AppLog.w()
        this.test.set(this.model)
    }

    fun itemClick(view: View) {
        AppLog.d(view)
        this.model.name = "ggg"
    }
}
