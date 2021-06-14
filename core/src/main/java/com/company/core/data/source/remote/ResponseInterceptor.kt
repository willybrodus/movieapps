package com.company.core.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

class ResponseInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val bodyString = response.body?.string()

        return response.newBuilder()
            .body(bodyString?.toResponseBody(response.body?.contentType()))
            .build()
    }

}