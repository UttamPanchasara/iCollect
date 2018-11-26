package com.uttampanchasara.scanner.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.uttampanchasara.scanner.di.component.ActivityComponent
import com.uttampanchasara.scanner.utils.PrefUtils
import java.util.*

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/19/2018
 */
abstract class BaseFragment : Fragment(), BaseView {

    override fun onAttachView(v: BaseView) {
    }

    override fun onDetachView() {

    }

    override fun onUnAuthorizedAccess() {
    }

    abstract fun getLayoutId(): Int

    private var mActivity: BaseActivity? = null

    abstract fun setupView(view: View, savedInstanceState: Bundle?)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view, savedInstanceState)
    }

    /**
     * Is toggle the system UI flag i.e status and navigation bar for fullscreen mode
     */
    fun enableFullScreen(isEnabled: Boolean) {
        if (isEnabled) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
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


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val locale = PrefUtils.getString(PrefUtils.PREF_LANG_LOCALE)
        setLocale(context!!, locale)

        if (context is BaseActivity) {
            val activity = context as BaseActivity
            this.mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    fun getActivityComponent(): ActivityComponent? {
        return mActivity!!.getActivityComponent()
    }

    /**
     * Set the app's locale to the one specified by the given String.
     *
     * @param context
     * @param localeSpec a locale specification as used for Android resources (NOTE: does not
     * support country and variant codes so far); the special string "system" sets
     * the locale to the locale specified in system settings
     * @return
     */
    fun setLocale(context: Context, localeSpec: String): Context {
        val locale: Locale
        if (localeSpec == "system") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = Resources.getSystem().configuration.locales.get(0)
            } else {
                locale = Resources.getSystem().configuration.locale
            }
        } else {
            locale = Locale(localeSpec)
        }
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, locale)
        } else {
            updateResourcesLegacy(context, locale)
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    fun showSnackbar(@StringRes message: Int,
                     @StringRes actionName: Int,
                     onActionClick: View.OnClickListener?) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, onActionClick)
                .show()
    }

    fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackbar(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackbarWithMargin(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).apply { view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams).apply { setMargins(leftMargin, topMargin, rightMargin, 215) } }.show()
    }

}