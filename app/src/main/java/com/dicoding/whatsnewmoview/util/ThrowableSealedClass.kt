package com.dicoding.whatsnewmoview.util

import com.dicoding.whatsnewmoview.util.NetworkStatus.ConnectException
import com.dicoding.whatsnewmoview.util.NetworkStatus.UNAUTHORIZED

sealed class StatusConnection{
    data class StatusUnauthorized(val value : Int= UNAUTHORIZED) : StatusConnection()
    data class StatusConnectException(val value : Int= ConnectException) : StatusConnection()
    data class StatusHasErrorMessage(val message : String) : StatusConnection()
    object StatusUnexpectedError : StatusConnection()
}
