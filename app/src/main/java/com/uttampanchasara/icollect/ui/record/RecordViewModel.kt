package com.uttampanchasara.icollect.ui.record

import android.content.Context
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import com.uttampanchasara.icollect.AppConstants
import com.uttampanchasara.icollect.BaseViewModel
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.getDate
import com.uttampanchasara.icollect.getTime
import com.uttampanchasara.icollect.ui.base.BaseView
import com.uttampanchasara.icollect.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/16/2018
 */
class RecordViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    companion object {
        val TAG = "RecordViewModel"
    }

    var mView: RecordView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as RecordView
    }

    fun searchRecord(query: String?, date: String?) {
        mCompositeDisposable.add(mDataManager.searchRecord(query, date)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe {
                    mView?.onSearchResult(it)
                })
    }

    fun generateCurrentRecordsHTML(date: String?) {
        mCompositeDisposable.add(mDataManager.getRecordsFromDate(date).map {
            return@map generateHTMLContent(it)
        }.subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    mView?.onHTMLContentAvailable(it)
                }, {
                    it.printStackTrace()
                    mView?.onHTMLContentAvailable("")
                }))
    }

    fun generateAllRecordsHTML() {
        mCompositeDisposable.add(mDataManager.getAllRecords().map {

            return@map generateHTMLContent(it)
        }.subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    mView?.onHTMLContentAvailable(it)
                }, {
                    it.printStackTrace()
                    mView?.onHTMLContentAvailable("")
                }))
    }

    private fun generateHTMLContent(recordList: List<RecordData>): String {
        var htmlRowData = ""

        val timeTD = "<td>TIME</td>\n"
        val nameTD = "<td>NAME</td>\n"
        val codeTD = "<td>CODE</td>\n"
        val numberTD = "<td>NUMBER</td>\n"

        for (i in 0..(recordList.size - 1)) {
            val record = recordList[i]

            htmlRowData += "<tr>\n" +
                    timeTD.replace("TIME", getTime(record.createdAt)) +
                    nameTD.replace("NAME", record.customerName) +
                    codeTD.replace("CODE", record.productId) +
                    numberTD.replace("NUMBER", record.customerNumber) +
                    "  </tr>"
        }

        return AppConstants.HTML_HEADER.replace("DOCUMENT_TITLE", "Records :" + getDate(System.currentTimeMillis())) + htmlRowData + AppConstants.HTML_FOOTER
    }

    fun createPrintJob(context: Context, webView: WebView?) {

        // Get a PrintManager instance
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

        val jobName = "Report_" + getDate(System.currentTimeMillis())

        // Get a print adapter instance
        val printAdapter: PrintDocumentAdapter
        printAdapter = if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView?.createPrintDocumentAdapter(jobName)!!
        } else {
            webView?.createPrintDocumentAdapter()!!
        }

        // Create a print job with name and adapter instance
        val printJob = printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())

        // Save the job object for later status checking
        //mPrintJobs.add(printJob)
    }
}