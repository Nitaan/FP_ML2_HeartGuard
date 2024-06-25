package com.nitamlII.heart_uasii.ui.simulasi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SimulasiViewModel(application: Application) : AndroidViewModel(application) {
    private val _result = MutableLiveData<Float?>()
    val result: LiveData<Float?> get() = _result

    fun setResultValue(value: Float) {
        _result.value = value
    }
}