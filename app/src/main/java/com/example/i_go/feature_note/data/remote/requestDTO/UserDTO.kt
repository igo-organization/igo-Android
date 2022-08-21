package com.example.i_go.feature_note.data.remote.requestDTO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("subjects")
    val subjects: String = "",
    @SerializedName("hospital")
    val hospital: Int = 0
)