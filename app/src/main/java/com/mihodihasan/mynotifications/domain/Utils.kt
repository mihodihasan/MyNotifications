package com.mihodihasan.mynotifications.domain

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getSimpleDateFormatter() = SimpleDateFormat("hh mm ss a - MMM dd, yyyy", Locale.ENGLISH)
}