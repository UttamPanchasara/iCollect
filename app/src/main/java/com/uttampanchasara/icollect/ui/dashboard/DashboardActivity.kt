package com.uttampanchasara.icollect.ui.dashboard

import android.arch.lifecycle.Observer
import android.content.Intent
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.getDate
import com.uttampanchasara.icollect.ui.base.BaseActivity
import com.uttampanchasara.icollect.ui.record.RecordActivity
import com.uttampanchasara.icollect.ui.users.UsersActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

/**
 * @since 12/10/2018
 */
class DashboardActivity : BaseActivity(), DashboardView {

    override fun getLayout(): Int {
        return R.layout.activity_dashboard
    }

    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mViewModel: DashboardViewModel

    companion object {
        val TAG = "DashboardActivity"
    }

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    override fun setUp() {
        mViewModel.onAttachView(this)
        btnStart.setOnClickListener {
            startRecordActivity()
        }

        txtUsers.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getUsers().observe(this, usersObserver)
        mDataManager.getRecords().observe(this, recordsObserver)
    }

    private val usersObserver = Observer<List<User>> {
        if (it?.isNotEmpty()!!) {
            txtUsers.text = it.size.toString()
        } else {
            txtUsers.text = "0"
        }
    }

    private val recordsObserver = Observer<List<RecordData>> {
        if (it?.isNotEmpty()!!) {
            txtTotalRecords.text = it.size.toString()
        } else {
            txtTotalRecords.text = "0"
        }

        val mCurrentDate = getDate(System.currentTimeMillis())
        mViewModel.getRecordsFromDate(mCurrentDate)
    }

    override fun onPause() {
        super.onPause()
        mDataManager.getRecords().removeObserver(recordsObserver)
        mDataManager.getUsers().removeObserver(usersObserver)
    }

    override fun onDetachView() {
        super.onDetachView()
        mViewModel.onDetachView()
    }

    override fun onRecordOfToday(records: Int) {
        txtToday.text = records.toString()
    }

    private fun startRecordActivity() {
        startActivity(Intent(this, RecordActivity::class.java))
    }
}