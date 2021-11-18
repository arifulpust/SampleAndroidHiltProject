package com.bd.jayson.data.network.interceptor

interface IGetMethodTracker {

    fun shouldConvertToGetRequest(urlEncodedFragmentString: String):Boolean
}