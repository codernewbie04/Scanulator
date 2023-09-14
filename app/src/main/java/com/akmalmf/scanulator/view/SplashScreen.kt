package com.akmalmf.scanulator.view

import android.os.Bundle
import android.os.Handler
import com.akmalmf.scanulator.R
import com.akmalmf.scanulator.abstraction.base.BaseActivity
import com.akmalmf.scanulator.view.main.MainActivity

class SplashScreen : BaseActivity() {
    override fun initView() {
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            MainActivity.start(this)
            finishAffinity()
        }, 2100)
    }

    override fun initObservable() {

    }
}