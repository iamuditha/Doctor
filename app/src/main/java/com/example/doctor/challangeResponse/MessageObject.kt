package com.example.doctor.challangeResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MessageObject(@SerializedName("type") private val type: MessageType, @SerializedName("msg") private val msg: String) : Serializable {

    fun getType(): MessageType {
        return type
    }

    fun getMessage(): String {
        return msg
    }

}