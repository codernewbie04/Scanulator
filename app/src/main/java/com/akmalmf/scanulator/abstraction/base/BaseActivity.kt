package com.akmalmf.scanulator.abstraction.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akmalmf.scanulator.BuildConfig
import com.akmalmf.scanulator.R
import com.akmalmf.scanulator.abstraction.data.HttpResult
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 12:55
 * akmalmf007@gmail.com
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract fun initView()

    abstract fun initObservable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (BuildConfig.THEME) {
            "green" -> setTheme(R.style.Theme_Scanulator_Green)
            "red" -> setTheme(R.style.Theme_Scanulator_Red)
        }
        initView()
        initObservable()
    }



    fun snackBarSuccess(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.green_800))
            .show()
    }

    fun snackBarError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_600))
            .show()
    }

    private fun showNoConnectionAlert(
        errorMessage: String = "Tidak ada koneksi!",
    ) {
        snackBarError(errorMessage)
    }

    private fun showTimeoutAlert(
        errorMessage: String = "Koneksi timeout. Mohon coba beberapa saat lagi",
    ) {
        snackBarError(errorMessage)
    }

    private fun showClientAlert(
        errorMessage: String = "Terjadi kesalahan pada aplikasi. Mohon coba beberapa saat lagi",
        message: String? = null
    ) {
        if (message != null) {
            snackBarError(message)
        } else {
            snackBarError(errorMessage)
        }
    }

    private fun showServerErrorAlert(
        errorMessage: String = "Terjadi kendala pada server. Mohon coba beberapa saat lagi",
    ) {
        snackBarError(errorMessage)
    }

    private fun showUnknownErrorAlert(
        errorMessage: String = "Terjadi kesalahan yang tak terduga. "
    ) {
        snackBarError(errorMessage)
    }

    fun showErrorAlert(cause: HttpResult, message: String? = null) {
        when (cause) {
            HttpResult.NO_CONNECTION -> showNoConnectionAlert()
            HttpResult.TIMEOUT -> showTimeoutAlert()
            HttpResult.CLIENT_ERROR -> showClientAlert(message = message)
            HttpResult.BAD_RESPONSE -> showUnknownErrorAlert()
            HttpResult.SERVER_ERROR -> showServerErrorAlert()
            HttpResult.NOT_DEFINED -> showUnknownErrorAlert()
        }
    }

}