package com.example.tem.home

import android.graphics.Bitmap

data class MyItem(
    var img:Bitmap?=null,
    var name:String ="",
    var date:String="",
    var deadline:String="",
    var memo:String="",
    var cycle:String?,
)
