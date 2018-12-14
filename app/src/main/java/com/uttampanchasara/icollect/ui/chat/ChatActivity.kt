package com.uttampanchasara.icollect.ui.chat

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.msg.ChatMessages
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.ui.base.BaseActivity
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject
import javax.inject.Inject

/**
 * @since 12/13/2018
 */
class ChatActivity : BaseActivity(), ChatView {

    override fun getLayout(): Int {
        return R.layout.activity_chat
    }

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_ID = "USER_ID"
        const val TAG = "ChatActivity"
    }

    @Inject
    lateinit var mViewModel: ChatViewModel

    @Inject
    lateinit var mSocket: Socket

    @Inject
    lateinit var mDataManager: DataManager

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    var userId: String = ""

    private lateinit var mAdapter: MessageListAdapter

    @SuppressLint("SetTextI18n")
    override fun setUp() {
        mViewModel.onAttachView(this)

        val userName = intent.getStringExtra(USER_NAME)
        userId = intent.getIntExtra(USER_ID, 0).toString()

        setToolbar(toolbar, userName, true)
        mSocket.emit("room", userId)

        mAdapter = MessageListAdapter(this)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = mAdapter

        btnSend.setOnClickListener {
            if (!edtMsg.text.toString().isEmpty()) {

                val chatMessages = ChatMessages(userId, userName, userId, edtMsg.text.toString(), ChatMessages.TYPE.SENT.value, System.currentTimeMillis())

                val jsonObject = JSONObject()
                jsonObject.put(ChatMessages.ROOM_ID, userId)
                jsonObject.put(ChatMessages.USER_NAME, userName)
                jsonObject.put(ChatMessages.USER_ID, userId)
                jsonObject.put(ChatMessages.MESSAGE, edtMsg.text.toString())
                jsonObject.put(ChatMessages.MESSAGE_TYPE, ChatMessages.TYPE.SENT.value)
                jsonObject.put(ChatMessages.TIME, System.currentTimeMillis())
                mViewModel.insertMessage(chatMessages)
                mSocket.emit("send-message", jsonObject)

                edtMsg.setText("")
            }
        }
    }

    private val chatMessagesObserver = Observer<List<ChatMessages>> {
        if (it?.isNotEmpty()!!) {
            mAdapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getChatMessages(userId).observe(this, chatMessagesObserver)
    }

    override fun onPause() {
        super.onPause()
        mDataManager.getChatMessages(userId).removeObserver(chatMessagesObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDetachView()
    }
}