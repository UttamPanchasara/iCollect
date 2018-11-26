package com.uttampanchasara.scanner.ui.base

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/20/2018
 */
interface BaseView {
    fun onAttachView(v: BaseView)
    fun onDetachView()

    fun hideLoading()

    fun showLoading()

    fun onError(errorMessage: String)

    fun onUnAuthorizedAccess()
}