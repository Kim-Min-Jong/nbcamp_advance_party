package com.sparta.week1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: Int,
    val title: String,
    val description: String
): Parcelable
