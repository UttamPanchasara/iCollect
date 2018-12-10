package com.uttampanchasara.icollect.ui.record

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.uttampanchasara.icollect.R
import kotlinx.android.synthetic.main.list_dates.view.*


/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/27/2018
 */
class DateListAdapter(context: Context, val dateSelectionListener: DateSelectionListener) : BaseAdapter() {

    internal var inflator: LayoutInflater
    var dateList: List<String>? = arrayListOf()

    init {
        inflator = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return dateList!!.size
    }

    fun setDataList(dateList: List<String>?) {
        this.dateList = dateList
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): String? {
        return dateList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflator.inflate(R.layout.list_dates, null)
        val date = dateList?.get(position)
        view.txtDate.text = date
        view.txtDate.setOnClickListener {
            view.ivCheck.visibility = View.VISIBLE
            view.txtDate.setBackgroundColor(Color.parseColor("#FF9800"))
            dateSelectionListener.onDateSelected(date)
        }
        return view
    }

    interface DateSelectionListener {
        fun onDateSelected(date: String?)
    }
}