package com.uttampanchasara.icollect.ui.dashboard

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ListPopupWindow
import androidx.core.widget.toast
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.getDate
import com.uttampanchasara.icollect.getTime
import com.uttampanchasara.icollect.shareRecord
import com.uttampanchasara.icollect.ui.addnew.AddNewActivity
import com.uttampanchasara.icollect.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject


class DashboardActivity : BaseActivity(), DashboardView,
        Observer<List<RecordData>>,
        SearchView.OnQueryTextListener,
        DateListAdapter.DateSelectionListener,
        RecordClickListener {

    override fun getLayout(): Int {
        return R.layout.activity_dashboard
    }

    companion object {
        val TAG = "DashboardActivity"
    }

    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mViewModel: DashboardViewModel

    lateinit var mListPopupWindow: ListPopupWindow

    var mSelectedDate: String? = ""

    private var mWebView: WebView? = null

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    lateinit var mAdapter: RecordListAdapter
    lateinit var mDateAdapter: DateListAdapter

    override fun setUp() {
        mViewModel.onAttachView(this)
        setToolbar(toolbar, "Records", false)
        btnAdd.setOnClickListener {
            //set current as selected
            mSelectedDate = getDate(System.currentTimeMillis())
            startActivity(Intent(this, AddNewActivity::class.java))
        }

        mDateAdapter = DateListAdapter(this, this)
        mListPopupWindow = ListPopupWindow(this)
        mListPopupWindow.setAdapter(mDateAdapter)
        mListPopupWindow.anchorView = toolbar
        mListPopupWindow.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent)))

        txtSelectDate.setOnClickListener {
            mListPopupWindow.show()
        }

        mAdapter = RecordListAdapter(this)
        rvRecords.adapter = mAdapter
        rvRecords.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getAllDates().observeForever {
            mDateAdapter.setDataList(it)
        }
        mSelectedDate = getDate(System.currentTimeMillis())
        fetchRecords()
    }

    override fun onPause() {
        mDataManager.getRecords().removeObserver(this)
        dismissDialog()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDetachView()
        dismissDialog()
    }

    fun dismissDialog() {
        //dismiss dialog if showing on pause/destroy to avoid window leak issue
        if (mListPopupWindow.isShowing) {
            mListPopupWindow.dismiss()
        }
    }

    override fun onDateSelected(date: String?) {
        mSelectedDate = date
        fetchRecords()

        dismissDialog()
    }

    private fun fetchRecords() {
        txtSelectDate.text = mSelectedDate
        mDataManager.getLiveRecordsFromDate(mSelectedDate).observe(this, this)
    }

    override fun onChanged(records: List<RecordData>?) {
        setDataList(records)
    }

    override fun onRecordShare(recordData: RecordData) {

        val shareData: String?
        val address: String? = if (recordData.address.isNotEmpty()) {
            "Address: " + recordData.address
        } else {
            "Address: NA"
        }
        val number: String? = if (recordData.number.isNotEmpty()) {
            "Number: " + recordData.number
        } else {
            "Number: NA"
        }

        shareData = "Name: " + recordData.name +
                "\nCode: " + recordData.code +
                "\n" + number +
                "\n" + address +
                "\nTime: " + getTime(recordData.time)

        shareRecord(shareData)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_print_all) {
            mViewModel.generateAllRecordsHTML()
            return true
        } else if (item.itemId == R.id.action_print_current_records) {
            mViewModel.generateCurrentRecordsHTML(mSelectedDate)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        mViewModel.searchRecord("%$query%", mSelectedDate)
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

    override fun onHTMLContentAvailable(htmlContent: String?) {
        val webView = WebView(this)
        if (htmlContent?.isNotEmpty()!!) {
            webView.loadData(htmlContent, "text/html", "UTF-8")
            webView.clearCache(true)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    mViewModel.createPrintJob(this@DashboardActivity, view)
                    mWebView = null
                }
            }
        } else {
            toast(getString(R.string.some_wrong))
        }
        mWebView = webView
    }
}