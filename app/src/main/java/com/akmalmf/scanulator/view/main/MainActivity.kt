package com.akmalmf.scanulator.view.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.akmalmf.scanulator.BuildConfig
import com.akmalmf.scanulator.R
import com.akmalmf.scanulator.abstraction.base.BaseActivity
import com.akmalmf.scanulator.abstraction.data.Status
import com.akmalmf.scanulator.core.model.ApiResponse
import com.akmalmf.scanulator.core.utils.convertToFile
import com.akmalmf.scanulator.core.utils.createCustomTempFile
import com.akmalmf.scanulator.core.utils.reduceFileImage
import com.akmalmf.scanulator.core.utils.toGone
import com.akmalmf.scanulator.core.utils.toInvisible
import com.akmalmf.scanulator.core.utils.toVisible
import com.akmalmf.scanulator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermission()
        }

        binding.buttonScan.setOnClickListener {
            if (allPermissionsGranted()) {
                if (BuildConfig.USE_FILESYSTEM) {
                    takePictureByFileSystem()
                } else {
                    takePictureByCamera()
                }
            } else {
                snackBarError("Please grant all permission")
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    private fun takePictureByFileSystem() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = convertToFile(this@MainActivity, selectedImg)
            binding.imageResult.setImageURI(selectedImg)
            viewModel.scanImage(reduceFileImage(myFile))
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val photo = File(currentPhotoPath)
            photo.let { file ->
                if (file != null) {
                    binding.imageResult.setImageBitmap(BitmapFactory.decodeFile(file.path))
                }
            }
            viewModel.scanImage(reduceFileImage(photo))
        }
    }

    private fun takePictureByCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainActivity, "${BuildConfig.APPLICATION_ID}.imageProvider", it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


    override fun initObservable() {
        viewModel.result.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    hideLoading()
                    val response = resource.data
                    response?.let { it ->
                        handleSuccessResponse(it)
                    } ?: snackBarError("Unknown error")
                }
                Status.ERROR -> {
                    hideLoading()
                    showErrorAlert(resource.cause, resource.data?.ErrorMessage?.getOrNull(0))
                }
            }
        }
    }

    private fun handleSuccessResponse(response: ApiResponse) {
        if (!response.IsErroredOnProcessing && response.OCRExitCode == 1) {
            val result = response.ParsedResults.getOrNull(0)?.ParsedText
            result?.takeIf { res -> res.isNotEmpty() }?.let {
                val pattern = Regex("\\d+[+\\-*/xX]\\d+") // find math expression in string (Ex: 2 + 4)
                val matchResult = pattern.find(result)

                if (matchResult != null) {
                    val expression = matchResult.value
                    binding.textScanned.text = expression
                    try {
                        binding.textResult.text =  calculateExpression(expression.lowercase()).toString()
                    } catch (e: Exception) {
                        snackBarError("Operasi tidak dapat dilakukan")
                    }

                } else {
                    snackBarError("Tidak ada ekspresi yang ditemukan dalam string.")
                }
            } ?: snackBarError("Tidak ada ekspresi yang ditemukan dalam string.")
        } else {
            val errorMessage = response.ErrorMessage.getOrNull(0) ?: "Unknown error"
            snackBarError(errorMessage)
        }
    }

    private fun calculateExpression(expression: String): Double {
        val parts = expression.split(Regex("[+\\-*/x]"))
        val operator = expression.find { it in setOf('+', '-', '*', '/', 'x') }

        if (parts.size == 2 && operator != null) {
            val operand1 = parts[0].toDouble()
            val operand2 = parts[1].toDouble()

            return when (operator) {
                '+' -> operand1 + operand2
                '-' -> operand1 - operand2
                '*' -> operand1 * operand2
                'x' -> operand1 * operand2
                '/' -> operand1 / operand2
                else -> throw IllegalArgumentException("Operator tidak valid: $operator")
            }
        } else {
            throw IllegalArgumentException("Ekspresi tidak valid: $expression")
        }
    }


    private fun showLoading() {
        binding.buttonScan.toInvisible()
        binding.progressBar.toVisible()
        binding.textScanned.text = getString(R.string.placeholder_board)
        binding.textResult.text = getString(R.string.placeholder_result)
    }

    private fun hideLoading() {
        binding.buttonScan.toVisible()
        binding.progressBar.toGone()
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            arrayOf(
                Manifest.permission.CAMERA
            )
        private const val REQUEST_CODE_PERMISSIONS = 10

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}