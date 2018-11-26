package com.uttampanchasara.baseprojectkotlin.ui.dashboard

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.uttampanchasara.baseprojectkotlin.R
import com.uttampanchasara.baseprojectkotlin.data.DataManager
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import com.uttampanchasara.baseprojectkotlin.di.component.ActivityComponent
import com.uttampanchasara.baseprojectkotlin.ui.addnew.AddNewActivity
import com.uttampanchasara.baseprojectkotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class DashboardActivity : BaseActivity(), DashboardView, Observer<List<RecordData>> {

    override fun getLayout(): Int {
        return R.layout.activity_dashboard
    }

    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mViewModel: DashboardViewModel

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    lateinit var mAdapter: RecordListAdapter

    override fun setUp() {
        setToolbar(toolbar, "Records", false)
        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddNewActivity::class.java))
        }

        mAdapter = RecordListAdapter()
        rvRecords.adapter = mAdapter
        rvRecords.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getRecords().observe(this, this)
    }

    override fun onPause() {
        super.onPause()
        mDataManager.getRecords().removeObserver(this)
    }

    override fun onChanged(records: List<RecordData>?) {
        mAdapter.setData(records!!)
    }
}