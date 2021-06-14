package com.dicoding.whatsnewmoview.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dicoding.whatsnewmoview.util.StatusConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    fun getBaseActivity() = activity as BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeChange()
    }

    abstract fun initViewModel()

    abstract fun observeChange()

    fun loading(loaded: Boolean) {
        getBaseActivity().loading(loaded)
    }

    fun showSnackbarError(message: String?) {
        getBaseActivity().showSnackbarError(message)
    }

    fun onErrorHandle(status : StatusConnection){
        getBaseActivity().onErrorHandle(status)
    }
}