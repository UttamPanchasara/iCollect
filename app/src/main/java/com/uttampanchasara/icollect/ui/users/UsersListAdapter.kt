package com.uttampanchasara.icollect.ui.users

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.ui.chat.ChatActivity
import kotlinx.android.synthetic.main.list_users.view.*

/**
 * @since 12/13/2018
 */
class UsersListAdapter(private val mContext: Context) : RecyclerView.Adapter<ViewHolder>() {

    var mUsersList: List<User> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_users, p0, false))
    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUsersList[position], mContext)
    }

    fun setData(users: List<User>) {
        mUsersList = users
        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User, mContext: Context) {
        itemView.txtName.text = user.userName
        itemView.txtEmail.text = user.userEmail
        itemView.txtNumber.text = user.userNumber

        itemView.setOnClickListener {
            val chatIntent = Intent(mContext, ChatActivity::class.java)
            chatIntent.putExtra(ChatActivity.USER_NAME, user.userName)
            chatIntent.putExtra(ChatActivity.USER_ID, user.id)
            mContext.startActivity(chatIntent)
        }
    }
}