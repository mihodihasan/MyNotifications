package com.mihodihasan.mynotifications.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mihodihasan.mynotifications.domain.Utils
import java.util.*

@Entity(tableName = "notification")
data class Notification(
    val title: String?,
    val message: String?,
    val time: Long,
    var appPackage: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) {
    fun getFormattedTime(): CharSequence? {
        return Utils.getSimpleDateFormatter().format(Date(time))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notification

        if (title != other.title) return false
        if (message != other.message) return false
        if (time != other.time) return false
        if (appPackage != other.appPackage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + appPackage.hashCode()
        return result
    }


}

