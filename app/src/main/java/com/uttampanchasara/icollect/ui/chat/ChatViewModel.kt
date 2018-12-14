package com.uttampanchasara.icollect.ui.chat

import android.util.Log
import com.uttampanchasara.icollect.BaseViewModel
import com.uttampanchasara.icollect.R.id.edtMsg
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.msg.ChatMessages
import com.uttampanchasara.icollect.ui.base.BaseView
import com.uttampanchasara.icollect.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject

/**
 * @since 12/13/2018
 */
class ChatViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable,
                    val mSocket: Socket) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    companion object {
        val TAG = "ChatViewModel"
    }

    var mView: ChatView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as ChatView

        mSocket.on("message") {

            val jsonObject = it[0] as JSONObject

            val chatMessages = ChatMessages(jsonObject.getString(ChatMessages.ROOM_ID),
                    jsonObject.getString(ChatMessages.USER_NAME),
                    jsonObject.getString(ChatMessages.USER_ID),
                    jsonObject.getString(ChatMessages.MESSAGE),
                    jsonObject.getInt(ChatMessages.MESSAGE_TYPE),
                    jsonObject.getLong(ChatMessages.TIME))
            insertMessage(chatMessages)
        }
    }

    fun insertMessage(chatMessages: ChatMessages) {
        mCompositeDisposable.add(mDataManager.insertMessage(chatMessages)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    Log.i(TAG, "New Message Inserted")
                }, {
                    it.printStackTrace()
                }))
    }
}