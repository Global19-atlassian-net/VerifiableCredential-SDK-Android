// Copyright (c) Microsoft Corporation. All rights reserved

package com.microsoft.did.sdk.di

import android.content.Context
import android.preference.PreferenceManager
import com.microsoft.correlationvector.CorrelationVector
import com.microsoft.did.sdk.util.Constants.CORRELATION_VECTOR_HEADER
import com.microsoft.did.sdk.util.Constants.CORRELATION_VECTOR_IN_PREF
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class CorrelationVectorInterceptor @Inject constructor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        val correlationVectorString = readCorrelationVector(context)
        val requestWithCorrelationVectorBuilder = originalRequest.newBuilder()
        if (correlationVectorString != null) {
            val correlationVectorIncremented = CorrelationVector.parse(correlationVectorString).increment()
            writeCorrelationVector(context, correlationVectorIncremented)
            requestWithCorrelationVectorBuilder.header(CORRELATION_VECTOR_HEADER, correlationVectorIncremented)
        }
        val requestWithCorrelationVector = requestWithCorrelationVectorBuilder.build()
        return chain.proceed(requestWithCorrelationVector)
    }

    private fun writeCorrelationVector(applicationContext: Context, correlationId: String) {
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString(CORRELATION_VECTOR_IN_PREF, correlationId).apply()
    }

    private fun readCorrelationVector(applicationContext: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(CORRELATION_VECTOR_IN_PREF, null)
    }
}