package com.uttampanchasara.icollect.ui.record

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.getTime
import kotlinx.android.synthetic.main.list_records.view.*

/**
 * @since 11/26/2018
 */
class RecordListAdapter(val mClickListener: RecordClickListener) : RecyclerView.Adapter<ViewHolder>() {

    var mRecordDataList: List<RecordData> = arrayListOf()

    companion object {
        var mSelectedPosition: Int = -1
        var mLastSelectedPosition: Int = -1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_records, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mRecordDataList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recordData = mRecordDataList[position]
        viewHolder.bindView(recordData)

        viewHolder.itemView.setOnClickListener {
            changeSelection(position)
        }

        viewHolder.itemView.btnShare.setOnClickListener {
            mClickListener.onRecordShare(recordData)
        }

        if (mSelectedPosition == position) {
            viewHolder.itemView.divider.visibility = View.VISIBLE
            viewHolder.itemView.txtNumber.visibility = View.VISIBLE
            viewHolder.itemView.txtAddress.visibility = View.VISIBLE
            viewHolder.itemView.root.setBackgroundResource(R.drawable.bg_selected_item)
        } else {
            viewHolder.itemView.divider.visibility = View.GONE
            viewHolder.itemView.txtNumber.visibility = View.GONE
            viewHolder.itemView.txtAddress.visibility = View.GONE
            viewHolder.itemView.root.setBackgroundResource(0)
        }
    }

    fun changeSelection(selectedPosition: Int) {
        //store position as last selected
        mLastSelectedPosition = mSelectedPosition

        if (mLastSelectedPosition == selectedPosition) {
            mSelectedPosition = -1
            notifyItemChanged(mLastSelectedPosition)
        } else {
            //add new position
            mSelectedPosition = selectedPosition

            notifyItemChanged(mLastSelectedPosition)
            notifyItemChanged(mSelectedPosition)
        }
    }


    fun setData(records: List<RecordData>) {
        mRecordDataList = records
        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(recordData: RecordData) {
        itemView.txtCode.text = recordData.productId
        itemView.txtName.text = recordData.customerName
        itemView.txtTime.text = getTime(recordData.createdAt)

        if (recordData.customerAddress.isNotEmpty()) {
            itemView.txtAddress.text = recordData.customerAddress
        } else {
            itemView.txtAddress.text = "NA"
        }

        if (recordData.customerNumber.isNotEmpty()) {
            itemView.txtNumber.text = recordData.customerNumber
        } else {
            itemView.txtNumber.text = "NA"
        }
    }
}

interface RecordClickListener {
    fun onRecordShare(recordData: RecordData)
}