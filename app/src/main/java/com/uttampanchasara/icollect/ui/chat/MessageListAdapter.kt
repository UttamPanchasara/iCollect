package com.uttampanchasara.icollect.ui.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.repository.msg.ChatMessages
import com.uttampanchasara.icollect.getMessageTime


class MessageListAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mMessageList: List<ChatMessages> = arrayListOf()

    override fun getItemCount(): Int {
        return mMessageList.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        return mMessageList[position].messageType
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        when (viewType) {
            ChatMessages.TYPE.SENT.value -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message_sent, parent, false)
                return SentMessageHolder(view)
            }
            ChatMessages.TYPE.RECEIVE.value -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message_received, parent, false)
                return ReceivedMessageHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message_sent, parent, false)
                return SentMessageHolder(view)
            }
        }
    }

    fun setData(messages: List<ChatMessages>) {
        mMessageList = messages
        notifyDataSetChanged()
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = mMessageList[position]

        when (holder.itemViewType) {
            message.messageType -> (holder as SentMessageHolder).bind(message)
            message.messageType -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    private inner class SentMessageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var messageText: TextView = itemView.findViewById(R.id.text_message_body)
        internal var messageTime: TextView = itemView.findViewById(R.id.text_message_time)
        internal fun bind(message: ChatMessages) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
            messageTime.text = getMessageTime(message.time)
        }
    }

    private inner class ReceivedMessageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var messageText: TextView = itemView.findViewById(R.id.txtMessage)
        internal var txtTime: TextView = itemView.findViewById(R.id.txtTime)
        internal var txtName: TextView = itemView.findViewById(R.id.txtName)
        internal var txtNameLetter: TextView = itemView.findViewById(R.id.txtNameLetter)

        internal fun bind(message: ChatMessages) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
            txtName.text = getMessageTime(message.time)

            txtName.text = message.userName
            txtNameLetter.setText(message.userName[0].toInt())
        }
    }
}