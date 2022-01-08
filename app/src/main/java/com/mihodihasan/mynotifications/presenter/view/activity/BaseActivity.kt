package com.mihodihasan.mynotifications.presenter.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mihodihasan.mynotifications.databinding.CustomToolbarBinding
import dagger.hilt.android.AndroidEntryPoint
import spencerstudios.com.bungeelib.Bungee
import timber.log.Timber

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.tag("AppFlow").d("onCreate from BaseActivity")
    }

    fun ViewBinding.getToolbar() = CustomToolbarBinding.bind(this.root)

    fun CustomToolbarBinding.updateToolbar(
        title: String,
        backListener: View.OnClickListener = View.OnClickListener {
            finish()
            Bungee.slideRight(this@BaseActivity)
        }
    ) {
        this.tvTitle.text = title
        this.ivBack.setOnClickListener(backListener)
    }
}