package com.dicoding.whatsnewmoview.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.HttpException
import com.dicoding.whatsnewmoview.BuildConfig
import com.dicoding.whatsnewmoview.util.NetworkStatus.UNAUTHORIZED
import com.dicoding.whatsnewmoview.util.StatusConnection
import com.dicoding.whatsnewmoview.util.StatusConnection.*
import com.google.gson.JsonParseException
import io.reactivex.disposables.CompositeDisposable
import java.net.SocketException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    private val _throwable = MutableLiveData<StatusConnection>()
    val throwable: LiveData<StatusConnection>
        get() = _throwable


    override fun onCleared() {
        with(compositeDisposable) {
            clear()
            dispose()
        }
        super.onCleared()
    }

    fun onNetworkErrorHandling(error: Throwable) {
        if (BuildConfig.DEBUG)
            error.printStackTrace()

        when (error) {
            is HttpException -> {
                when (error.statusCode) {
                    UNAUTHORIZED  -> {
                        _throwable.value = StatusUnauthorized()
                    }
                    404 -> {
                        _throwable.value = StatusHasErrorMessage("There is no page found")
                    }
                    else -> {
                        try {
                            _throwable.value = StatusHasErrorMessage(error.message?:"")
                        } catch (e: JsonParseException) {
                            _throwable.value = StatusUnexpectedError
                        }
                    }
                }

            }

            is SocketException -> {
                _throwable.value = StatusConnectException()
            }
            else -> _throwable.value = StatusUnexpectedError
        }


    }
}