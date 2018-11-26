package com.uttampanchasara.baseprojectkotlin.ui.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.toast
import com.uttampanchasara.baseprojectkotlin.AppController
import com.uttampanchasara.baseprojectkotlin.di.component.ActivityComponent
import com.uttampanchasara.baseprojectkotlin.di.component.DaggerActivityComponent
import com.uttampanchasara.baseprojectkotlin.di.module.ActivityModule
import com.uttampanchasara.baseprojectkotlin.utils.PrefUtils
import java.io.IOException

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/13/2018
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    val REQUEST_CHECK_SETTINGS = 10001

    abstract fun getLayout(): Int

    override fun onAttachView(v: BaseView) {
    }

    override fun onDetachView() {

    }

    override fun onUnAuthorizedAccess() {
    }

    override fun onError(errorMessage: String) {
        toast(errorMessage)
    }

    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as AppController).mAppComponent)
                .activityModule(ActivityModule(this))
                .build()
        injectComponents(mActivityComponent)

        super.onCreate(savedInstanceState)
        if (getLayout() != 0) {
            setContentView(getLayout())

            val isAccepted = requestPermission(Manifest.permission.CAMERA)
            if (isAccepted) {
                setUp()
            }
        } else {
            throw Exception("Layout must not be empty")
        }
    }

    fun getActivityComponent(): ActivityComponent {
        return mActivityComponent
    }

    fun loadFragment(isAddToBackStack: Boolean = false, transaction: FragmentTransaction.() -> Unit) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.transaction()
        /*for ((name, view) in transitionPairs) {
            ViewCompat.setTransitionName(view, name)
            beginTransaction.addSharedElement(view, name)
        }*/

        if (isAddToBackStack) beginTransaction.addToBackStack(null)
        beginTransaction.commit()
    }

    abstract fun injectComponents(mActivityComponent: ActivityComponent)

    abstract fun setUp()

    /**
     * Set the toolbar of the activity.
     *
     * @param toolbarId    resource id of the toolbar
     * @param title        title of the activity
     * @param showUpButton true if toolbar should display up indicator.
     */
    protected fun setToolbar(toolbar: Toolbar, title: String, showUpButton: Boolean) {
        setSupportActionBar(toolbar)
        setToolbar(title, showUpButton)
    }


    /**
     * Set the toolbar.
     *
     * @param title        Activity title string.
     * @param showUpButton true if toolbar should display up indicator.
     */
    @SuppressLint("RestrictedApi")
    protected fun setToolbar(title: String, showUpButton: Boolean) {
        //set the title
        supportActionBar!!.title = title

        //Set the up indicator
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(showUpButton)
        supportActionBar!!.setHomeButtonEnabled(showUpButton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(showUpButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            false
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_CODE = 12
    }

    /**
     * Is toggle the system UI flag i.e status and navigation bar for fullscreen mode
     */
    fun enableFullScreen(isEnabled: Boolean) {
        if (isEnabled) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    /**
     * Hide the keyboard.
     *
     * @param view View in focus.
     */
    @JvmOverloads
    fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = view.context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun requestPermission(permissionName: String): Boolean {
        // Here, thisActivity is the current activity
        val isGranted = ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            // Permission is not granted
            // Should we show an explanation?

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, arrayOf(permissionName), MY_PERMISSIONS_REQUEST_CODE)
        }
        return isGranted
    }

    protected open fun onPermissionResult(isGranted: Boolean, permissionName: String) {
        if (isGranted) {
            setUp()
        } else {
            toast("Without required permissions app will not work!")
            finishAffinity()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onPermissionResult(true, permissions[0])
                } else {
                    onPermissionResult(false, permissions[0])
                }
            }
        }
    }

    fun loadJSONFromAsset(name: String): String {
        var json: String? = null
        try {
            val inputStream = this.assets.open(name)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }

        return json
    }

    fun signOutUnAuthorized(intent: Intent) {
        PrefUtils.clear()
        startActivity(intent)
    }

    fun onFragmentAttached() {

    }

    fun onFragmentDetached(tag: String) {

    }
}