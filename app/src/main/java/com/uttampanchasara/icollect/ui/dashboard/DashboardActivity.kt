package com.uttampanchasara.icollect.ui.dashboard

import android.arch.lifecycle.Observer
import android.content.Intent
import android.util.Log
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.getDate
import com.uttampanchasara.icollect.getTimeWithTFormat
import com.uttampanchasara.icollect.ui.base.BaseActivity
import com.uttampanchasara.icollect.ui.record.RecordActivity
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONObject
import javax.inject.Inject

/**
 * @since 12/10/2018
 */
class DashboardActivity : BaseActivity(), DashboardView, Observer<List<RecordData>> {

    override fun getLayout(): Int {
        return R.layout.activity_dashboard
    }

    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mSocket: Socket

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

        mSocket.on("new_record", object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                try {
                    val recordObject = args[0] as JSONObject
                    Log.d(TAG, recordObject.toString())

                    var createdTime: Long = 0
                    val createdAt = recordObject.getString("createdAt")
                    if (!createdAt.isNullOrEmpty()) {
                        createdTime = getTimeWithTFormat(createdAt)
                    }
                    val recordData = RecordData(createdTime,
                            recordObject.getString("createdDate"),
                            recordObject.getString("customerAddress"),
                            recordObject.getString("productId"),
                            recordObject.getString("customerName"),
                            recordObject.getString("customerNumber"))

                    mViewModel.insertRecord(recordData)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        mSocket.on("users", object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                try {
                    val recordObject = args[0] as JSONObject
                    Log.d(TAG, recordObject.toString())

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getRecords().observe(this, this)
    }

    override fun onPause() {
        super.onPause()
        mDataManager.getRecords().removeObserver(this)
    }

    override fun onChanged(it: List<RecordData>?) {
        if (it?.isNotEmpty()!!) {
            txtTotalRecords.text = it.size.toString()
        } else {
            txtTotalRecords.text = "0"
        }

        val mCurrentDate = getDate(System.currentTimeMillis())
        mViewModel.getRecordsFromDate(mCurrentDate)
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