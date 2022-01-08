package com.mihodihasan.mynotifications.domain

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getSimpleDateFormatter() = SimpleDateFormat("hh mm ss a - MMM dd, yyyy", Locale.ENGLISH)
    fun getSimpleDateFormatterOnlyTime() = SimpleDateFormat("hh mm ss a", Locale.ENGLISH)
    fun getSimpleDateFormatterFullDate() = SimpleDateFormat(" MMM, yyyy", Locale.ENGLISH)
    fun getSimpleDateFormatterOnlyDate() = SimpleDateFormat("dd", Locale.ENGLISH)

    fun getFormattedDate(time: Long): String {
        val date = Date(time)
        val onlyDate = getSimpleDateFormatterOnlyDate().format(date).toInt()
        val onlyDateStyled: String = getStyledDate(onlyDate)
        val restDatePart = getSimpleDateFormatterFullDate().format(date)
        return onlyDateStyled.plus(restDatePart)
    }

    private fun getStyledDate(onlyDate: Int): String {
        return when (onlyDate) {
            1, 21, 31 -> "${onlyDate}st"
            2, 22 -> "${onlyDate}nd"
            3 -> "${onlyDate}rd"
            23 -> "${onlyDate}rd"
            else -> "${onlyDate}th"
        }
    }

    fun getAppNameFromPackageName(applicationContext: Application, packageName: String): String {
        var appName: String = packageName
        val ai: ApplicationInfo? = try {
            applicationContext.packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        (if (ai != null) appName =
            applicationContext.packageManager?.getApplicationLabel(ai).toString())
        return appName
    }

    fun getIconFromPackage(applicationContext: Application, packageName: String): Drawable? {
        return try {
            val icon: Drawable =
                applicationContext.packageManager.getApplicationIcon(packageName)
            icon
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}