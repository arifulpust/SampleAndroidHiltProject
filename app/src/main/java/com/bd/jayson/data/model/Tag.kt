package com.bd.jayson.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(@Json(name = "name")
                  var name: String = ""): Parcelable {


}