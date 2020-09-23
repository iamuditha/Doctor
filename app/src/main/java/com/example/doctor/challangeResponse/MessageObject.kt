package com.example.doctor.challangeResponse

import java.io.Serializable


class MessageObject(private val type: MessageType, private val msg: String) : Serializable {

    fun getType(): MessageType {
        return type
    }

    fun getMessage(): String {
        return msg
    }

}