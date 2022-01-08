package com.mihodihasan.mynotifications.domain

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getSimpleDateFormatter() = SimpleDateFormat("hh mm ss a - MMM dd, yyyy", Locale.ENGLISH)

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
        /*
        String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));

        */
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