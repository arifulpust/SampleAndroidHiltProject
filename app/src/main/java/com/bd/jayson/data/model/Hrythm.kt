package com.bd.jayson.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hrythm(
    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "emotion")
    var emotion: String = "",
    @Json(name = "learning")
    var learning: String = "",
    @Json(name = "date_in_month")
    var date_in_month: String = "",
    @Json(name = "date")
    var date: String = "",
  @Json(name = "learning_date")
    var learning_date: String = "",

    @Json(name = "type")
    var type: Int = 0
) : Parcelable {


}