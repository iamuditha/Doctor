package com.example.doctor

import java.util.*

class GoogleDriveFileHolder {
    private var id: String? = null
    private var name: String? = null
    private var modifiedTime: Date? = null
    private var size: Long = 0
    private var createdTime: Date? = null
    private var starred: Boolean? = null
    private var webContentLink: String?  = null

    fun getWebContentLink(): String?{
        return webContentLink
    }

    fun setWebContentLink(webContentLink: String): String?{
        return this.webContentLink
    }

    fun getCreatedTime(): Date? {
        return createdTime
    }

    fun setCreatedTime(createdTime: Date?) {
        this.createdTime = createdTime
    }

    fun getStarred(): Boolean? {
        return starred
    }

    fun setStarred(starred: Boolean?) {
        this.starred = starred
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getModifiedTime(): Date? {
        return modifiedTime
    }

    fun setModifiedTime(modifiedTime: Date?) {
        this.modifiedTime = modifiedTime
    }

    fun getSize(): Long {
        return size
    }

    fun setSize(size: Long) {
        this.size = size
    }
}