package com.uttampanchasara.icollect.ui.users

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_users.*
import javax.inject.Inject

/**
 * @since 12/13/2018
 */
class UsersActivity : BaseActivity(), UsersView, Observer<List<User>> {

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    @Inject
    lateinit var mViewModel: UsersViewModel

    @Inject
    lateinit var mDataManager: DataManager

    lateinit var mAdapter: UsersListAdapter

    override fun getLayout(): Int {
        return R.layout.activity_users
    }

    override fun setUp() {
        mViewModel.onAttachView(this)
        setToolbar(toolbar, "Users", true)

        mAdapter = UsersListAdapter()
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        mDataManager.getUsers().observe(this, this)
    }

    override fun onPause() {
        super.onPause()
        mDataManager.getUsers().removeObserver(this)
    }

    override fun onChanged(users: List<User>?) {
        setDataList(users)
    }

    private fun setDataList(users: List<User>?) {
        val list = users!!
        if (list.isEmpty()) {
            placeholder.visibility = View.VISIBLE
        } else {
            placeholder.visibility = View.GONE
        }
        mAdapter.setData(list)
    }
}