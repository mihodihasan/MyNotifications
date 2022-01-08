package com.mihodihasan.mynotifications.presenter.model

sealed class MessageViewType(open val type: Int) {
    class DateType(override val type: Int = 0) : MessageViewType(type)
    class TextType(override val type: Int = 1) : MessageViewType(type)
}
