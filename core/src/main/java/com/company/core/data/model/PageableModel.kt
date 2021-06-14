package com.company.core.data.model

import java.io.Serializable

data class PageableModel<out T : Any>(
    val results: T,
    val page: Int,
    val total_pages : Int,
    val total_results : Int,
    val status_message : String? = null,
    val status_code : Int? = null
) : Serializable