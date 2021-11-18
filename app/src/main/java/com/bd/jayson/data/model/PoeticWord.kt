package com.bd.jayson.data.model

import com.squareup.moshi.Json

class PoeticWord {
    @Json(name = "name")
    var name: String = ""
    var icChecked: Boolean = false

    constructor(name: String){
       this. name=name
    }
}