package com.uttampanchasara.scanner.ui.dashboard

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.uttampanchasara.scanner.R
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.data.repository.record.RecordData
import com.uttampanchasara.scanner.di.component.ActivityComponent
import com.uttampanchasara.scanner.ui.addnew.AddNewActivity
import com.uttampanchasara.scanner.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject


class DashboardActivity : BaseActivity(), DashboardView, Observer<List<RecordData>>, SearchView.OnQueryTextListener {


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
        mViewModel.onAttachView(this)
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
        mDataManager.getRecords().removeObserver(this)
        mViewModel.onDetachView()
        super.onPause()
    }

    override fun onChanged(records: List<RecordData>?) {
        setDataList(records)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_dashboard, menu)

        val searchItem = menu.findItem(R.id.action_search)

        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        mViewModel.searchRecord("%" + query + "%")
        return true
    }

    override fun onSearchResult(records: List<RecordData>?) {
        setDataList(records)
    }

    private fun setDataList(records: List<RecordData>?) {
        val list = records!!
        if (list.isEmpty()) {
            placeholder.visibility = View.VISIBLE
        } else {
            placeholder.visibility = View.GONE
        }
        mAdapter.setData(list)
    }
}