package com.mihodihasan.mynotifications.presenter.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mihodihasan.mynotifications.databinding.CustomToolbarBinding
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.animateFinish
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.gone
import dagger.hilt.android.AndroidEntryPoint
import spencerstudios.com.bungeelib.Bungee
import timber.log.Timber

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.tag("AppFlow").d("onCreate from BaseActivity")
    }

    fun ViewBinding.getToolbar() = CustomToolbarBinding.bind(this.root)

    fun CustomToolbarBinding.updateToolbar(
        title: String, hideBackArrow: Boolean = false,
        backListener: View.OnClickListener = View.OnClickListener {
            animateFinish()
            Bungee.slideRight(this@BaseActivity)
        }
    ) {
        this.tvTitle.text = title
        if (hideBackArrow) this.ivBack.gone()
        this.ivBack.setOnClickListener(backListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideRight(this)
    }
}