package com.dicoding.whatsnewmoview.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.dicoding.whatsnewmoview.R
import com.dicoding.whatsnewmoview.util.StatusConnection
import com.dicoding.whatsnewmoview.widget.TopSnackbar
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy {
        ProgressDialog(this, R.style.MyAlertDialogStyle)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarWhite()
        observeChange()
    }

    fun loading(loaded: Boolean) {
        if (loaded) showProgress() else hideProgress()
    }

    private fun showProgress() {
        with(progressDialog) {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.layout_progress_dialog)
            isIndeterminate = true
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun hideProgress() {
        progressDialog.dismiss()
    }

    fun showSnackbarError(message: String?) {
        message?.let {
            val container = findViewById<CoordinatorLayout>(R.id.layout_content_coordinator)
            if (container != null) {
                showTopSnackbar(container, it, R.color.red)
            } else {
                showTopSnackbar(window.decorView.rootView, it, R.color.red)
            }
        }
    }

    private fun onUnexpectedError() {
        hideProgress()
        showSnackbarError(getString(R.string.msg_unexpected_error))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showTopSnackbar(root: View, message: String, @ColorRes color: Int) {
        val topSnackbar = TopSnackbar.make(root, message, TopSnackbar.LENGTH_LONG)
        val snackbarView = topSnackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, color))
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        topSnackbar.show()
    }

    open fun changeStatusBarWhite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }


    fun onErrorHandle(status : StatusConnection){
        when(status){
            is StatusConnection.StatusUnauthorized -> {
                showSnackbarError(getString(R.string.msg_unexpected_unauthorized))
            }
            is StatusConnection.StatusUnexpectedError -> {
                onUnexpectedError()
            }
            is StatusConnection.StatusConnectException -> {
                showSnackbarError(getString(R.string.msg_unexpected_network))
            }
            is StatusConnection.StatusHasErrorMessage -> {
                showSnackbarError(status.message)
            }
        }


    }

    abstract fun observeChange() //abstrac method for register livedata observer

}