package com.company.core.data.model

sealed class RemoteState<out T : Any>{
    data class RemoteData<out T : Any>(val result : T) : RemoteState<T>()
    data class InProgress(var inProgress : Boolean = false) : RemoteState<Nothing>()
}