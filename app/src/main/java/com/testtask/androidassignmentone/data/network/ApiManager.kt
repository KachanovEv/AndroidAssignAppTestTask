package com.testtask.androidassignmentone.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.testtask.androidassignmentone.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {

    private var restApi: RestApi
    private var gson: Gson
    private var readTimeout = 1L
    private var writeTimeout = 2L

    init {
        gson = createGson()
        restApi = Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(initClient())
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build().create(RestApi::class.java)
    }

    private fun initClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(interceptor)
            readTimeout(readTimeout, TimeUnit.MINUTES)
            connectTimeout(readTimeout, TimeUnit.MINUTES)
            writeTimeout(writeTimeout, TimeUnit.MINUTES)
            addNetworkInterceptor { chain ->
                val request: Request
                val original = chain.request()
                val originalBuilder = original.newBuilder()
                originalBuilder.addHeader("Content-PresentationsType", "application/json")
                request = originalBuilder.build()
                chain.proceed(request)
            }
        }.build()
    }

    private fun createGson(): Gson {
        return GsonBuilder().apply {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            setLenient()
        }.create()
    }

    fun restApiNoHeader(): RestApi {
        return restApi
    }
}