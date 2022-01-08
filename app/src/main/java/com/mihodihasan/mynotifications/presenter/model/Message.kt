package com.mihodihasan.mynotifications.presenter.model

import com.mihodihasan.mynotifications.data.model.Notification

data class Message(
    val type: MessageViewType,
    val date: String,
    val notification: Notification? = null
)
