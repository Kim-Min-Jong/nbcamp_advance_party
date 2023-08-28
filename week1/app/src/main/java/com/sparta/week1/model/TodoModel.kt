package com.sparta.week1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: String,
    var title: String,
    var description: String,
    var isChecked: Boolean
) : Parcelable
