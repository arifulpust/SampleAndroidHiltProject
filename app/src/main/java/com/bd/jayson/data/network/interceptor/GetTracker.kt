package com.bd.jayson.data.network.interceptor

import android.util.Log
import okhttp3.internal.immutableListOf

class GetTracker:IGetMethodTracker {

    private val getUrlList = immutableListOf(
        "/catFs-vF2/1/",
    )

    override fun shouldConvertToGetRequest(urlEncodedFragmentString: String):Boolean {
        Log.i("PATH PATH",urlEncodedFragmentString)
        val resultString = getUrlList.find {
            urlEncodedFragmentString.contains(it)
        }
        Log.i("PATH",resultString?:"No String")
        return !resultString.isNullOrEmpty()
    }
}