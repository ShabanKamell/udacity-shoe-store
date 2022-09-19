package com.udacity.shoestore.shoeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {

    private val _shoes = MutableLiveData<List<Shoe>>()
    val shoes: LiveData<List<Shoe>>
        get() = _shoes

    fun addItem(item: Shoe) {
        val items = mutableListOf<Shoe>()
        _shoes.value?.let {
            items.addAll(it)
        }
        items.add(item)
        _shoes.value = items
    }
}
