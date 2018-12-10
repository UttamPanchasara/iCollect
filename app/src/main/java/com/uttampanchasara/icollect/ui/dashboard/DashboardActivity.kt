package com.uttampanchasara.icollect.ui.dashboard

import android.content.Intent
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.getDate
import com.uttampanchasara.icollect.ui.base.BaseActivity
import com.uttampanchasara.icollect.ui.record.RecordActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

/**
 * @since 12/10/2018
 */
class DashboardActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_dashboard
    }

    @Inject
    lateinit var mDataManager: DataManager

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    override fun setUp() {
        btnStart.setOnClickListener {
            startRecordActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        val mCurrentDate = getDate(System.currentTimeMillis())
        mDataManager.getLiveRecordsFromDate(mCurrentDate).observeForever {
            if (it?.isNotEmpty()!!) {
                txtToday.text = it.size.toString()
            } else {
                txtToday.text = "0"
            }
        }

        mDataManager.getRecords().observeForever {
            if (it?.isNotEmpty()!!) {
                txtTotalRecords.text = it.size.toString()
            } else {
                txtTotalRecords.text = "0"
            }
        }
    }

    private fun startRecordActivity() {
        startActivity(Intent(this, RecordActivity::class.java))
    }
}