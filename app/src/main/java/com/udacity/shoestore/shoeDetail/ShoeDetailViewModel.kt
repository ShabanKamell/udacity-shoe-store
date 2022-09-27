package com.udacity.shoestore.shoeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeDetailViewModel : ViewModel() {
    private val _onSave = MutableLiveData<Boolean>()
    val onSave: LiveData<Boolean>
        get() = _onSave

    fun save() {
        _onSave.value = true
    }
}
