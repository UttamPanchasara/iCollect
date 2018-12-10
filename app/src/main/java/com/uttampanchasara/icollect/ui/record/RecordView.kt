package com.uttampanchasara.icollect.ui.record

import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.ui.base.BaseView

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/20/2018
 */
interface RecordView : BaseView {
    fun onSearchResult(records: List<RecordData>?)

    fun onHTMLContentAvailable(htmlContent: String?)
}