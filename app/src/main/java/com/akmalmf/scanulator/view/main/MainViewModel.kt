package com.akmalmf.scanulator.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalmf.scanulator.abstraction.data.Resource
import com.akmalmf.scanulator.core.model.ApiResponse
import com.akmalmf.scanulator.core.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:00
 * akmalmf007@gmail.com
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel(){
    private val _result = MutableLiveData<Resource<ApiResponse>>()
    val result: LiveData<Resource<ApiResponse>>
        get() = _result

    fun scanImage(image: File){
        val requestImageFile =
            image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = requestImageFile.let { it1 ->
            MultipartBody.Part.createFormData(
                "file",
                image.name,
                it1
            )
        }
        viewModelScope.launch(Dispatchers.IO){
            repository.scanImage(imageMultipart).collect{
                _result.postValue(it)
            }
        }
    }
}