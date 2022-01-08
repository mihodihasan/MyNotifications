package com.mihodihasan.mynotifications.domain

import android.app.Activity
import android.content.Intent
import android.view.View
import spencerstudios.com.bungeelib.Bungee

object ExtensionFunctions {
    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun Activity.animateStartActivity(intent: Intent) {
        startActivity(intent)
        Bungee.slideLeft(this)
    }

    fun Activity.animateFinish() {
        Bungee.slideRight(this)
        finish()
    }
}