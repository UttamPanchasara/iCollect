package com.uttampanchasara.scanner.ui.dashboard

import com.uttampanchasara.scanner.data.repository.record.RecordData
import com.uttampanchasara.scanner.ui.base.BaseView

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/20/2018
 */
interface DashboardView : BaseView {
    fun onSearchResult(records: List<RecordData>?)
}