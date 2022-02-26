package com.example.toyproject018_finedust.data.models.airquality

import androidx.annotation.ColorRes
import com.example.toyproject018_finedust.R
import com.google.gson.annotations.SerializedName

enum class Grade(
    val label: String,
    val emoji: String,
    @ColorRes val colorResId:Int
) {

    @SerializedName("1")
    GOOD("좋음","\uD83D\uDE3A", R.color.blue),

    @SerializedName("2")
    NORMAL("보통","\uD83D\uDE3C", R.color.green),

    @SerializedName("3")
    BAD("나쁨","\uD83D\uDE40", R.color.yellow),

    @SerializedName("4")
    AWFUL("매우 나쁨","\uD83D\uDE3F", R.color.red),

    UNKNOWN("미측정","\uD83D\uDE3E",R.color.gray);

    override fun toString(): String {
        return "$label $emoji"
    }
}