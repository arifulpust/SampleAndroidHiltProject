package com.bd.jayson.di.module

import android.content.Context
import coil.util.CoilUtils
import com.bd.jayson.BuildConfig


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.bd.jayson.data.network.interceptor.JaysonDns
import com.bd.jayson.data.network.retrofit.BeatsApi
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class EncryptedHttpClient

@Qualifier
annotation class SimpleHttpClient

@Qualifier
annotation class DnsHttpClient

@Qualifier
annotation class DefaultCache

@Qualifier
annotation class CoilCache

@Qualifier
annotation class DbRetrofit

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    @EncryptedHttpClient
    fun providesEncryptedHttpClient(@DefaultCache cache: Cache, beatsDns: JaysonDns): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
            }
            cache(cache)
            dns(beatsDns)
          //  addInterceptor(AuthInterceptor(GetTracker()))
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    @DefaultCache
    fun getCacheIterator(@ApplicationContext ctx: Context): Cache{
        val cacheSize = 25 * 1024 * 1024 // 25 MB
        return Cache(ctx.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    @CoilCache
    fun getCoilCache(@ApplicationContext ctx: Context): Cache {
        return CoilUtils.createDefaultCache(ctx)
    }
    
    @Provides
    @Singleton
    fun providesRetrofit(@EncryptedHttpClient httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("BEATS_BASE_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesbeatsApi(retrofit: Retrofit): BeatsApi {
        return retrofit.create(BeatsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesbeatsDns(@SimpleHttpClient httpClient: OkHttpClient): DnsOverHttps {
        return DnsOverHttps.Builder()
            .client(httpClient)
            .url("https://dns.google/dns-query".toHttpUrl())
            .build()
    }

    @Provides
    @Singleton
    @SimpleHttpClient
    fun providesSimpleHttpClient(@DefaultCache cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    @DnsHttpClient
    fun providesDnsHttpClient(@SimpleHttpClient simpleHttpClient: OkHttpClient, beatsDns: JaysonDns): OkHttpClient {
        return simpleHttpClient.newBuilder()
            .dns(beatsDns)
            .build()
    }

    @DbRetrofit
    @Singleton
    @Provides
    fun providesDbRetrofit(@SimpleHttpClient httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://real-db.text.com/")
            .build()
    }


}