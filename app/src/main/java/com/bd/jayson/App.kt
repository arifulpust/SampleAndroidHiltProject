package com.bd.jayson

import android.app.Application
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import com.bd.jayson.data.network.interceptor.CoilInterceptor
import com.bd.jayson.di.databinding.CustomBindingComponentBuilder
import com.bd.jayson.di.databinding.CustomBindingEntryPoint
import com.facebook.stetho.Stetho
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp

import com.bd.jayson.di.module.AppCoroutineScope
import com.bd.jayson.di.module.CoilCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class App : Application() {
    @Inject
    @CoilCache
    lateinit var coilCache: Cache
    @Inject
    @AppCoroutineScope
    lateinit var coroutineScope: CoroutineScope
    @Inject lateinit var coilInterceptor: CoilInterceptor
    @Inject lateinit var bindingComponentProvider: Provider<CustomBindingComponentBuilder>
    override fun onCreate() {
        super.onCreate()

        val dataBindingComponent = bindingComponentProvider.get().build()
        val dataBindingEntryPoint = EntryPoints.get(
            dataBindingComponent, CustomBindingEntryPoint::class.java
        )
        DataBindingUtil.setDefaultComponent(dataBindingEntryPoint)
        Stetho.initializeWithDefaults(this)
      //  initCalligraphy()

//        FacebookSdk.setIsDebugEnabled(true);
//        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        initCoil()

    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

//    private fun initCalligraphy() {
//        val mCalligraphyConfig = CalligraphyConfig.Builder()
//            // Todo (Gazi rimon ) add default here
//            //  .setDefaultFontPath(DEFAULT_FONT_PATH)
//            .setFontAttrId(R.attr.fontPath)
//            .build()
//        val mInterceptor = CalligraphyInterceptor(mCalligraphyConfig)
//        val mViewPumpBuilder = ViewPump.builder()
//            .addInterceptor(mInterceptor)
//            .build()
//        ViewPump.init(mViewPumpBuilder)
//        dev.b3nedikt.viewpump.ViewPump.init(RewordInterceptor!!)
//    }
    private fun initCoil() {
        val imageLoader = ImageLoader.Builder(this).apply {
            crossfade(true)
//            availableMemoryPercentage(0.2)
//            bitmapPoolPercentage(0.4)
            okHttpClient {
                OkHttpClient.Builder()
                    .cache(coilCache)
                    .addInterceptor(coilInterceptor)
                    .build()
            }

        }.build()
        Coil.setImageLoader(imageLoader)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        with(imageLoader) {
            bitmapPool.clear()
            memoryCache.clear()
        }
    }

}
