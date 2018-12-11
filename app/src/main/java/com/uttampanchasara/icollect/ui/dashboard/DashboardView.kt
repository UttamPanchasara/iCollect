package com.uttampanchasara.icollect.ui.dashboard

import com.uttampanchasara.icollect.ui.base.BaseView

/**
 * @since 12/11/2018
 */
interface DashboardView : BaseView {
    fun onRecordOfToday(records: Int)
}