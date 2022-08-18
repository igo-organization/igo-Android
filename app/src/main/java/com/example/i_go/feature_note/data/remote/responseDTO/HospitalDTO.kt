package com.example.i_go.feature_note.data.remote.responseDTO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HospitalDTO(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = "",

    @SerializedName("drawing")
    @Expose
    val drawing: String? = ""

)
