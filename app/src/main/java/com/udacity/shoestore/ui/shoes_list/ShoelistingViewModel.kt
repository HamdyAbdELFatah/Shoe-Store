package com.udacity.shoestore.ui.shoes_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoelistingViewModel : ViewModel() {
    private val _shoesList = MutableListLiveData<Shoe>()
    val shoesList: LiveData<List<Shoe>> = _shoesList

    val state = Shoe("", "", "", "", listOf())


    fun addShoe(shoe: Shoe) {

        _shoesList.add(shoe)
        Log.d("onViewCreated", "addShoe: ")
    }

}
class MutableListLiveData<T>(private val list: MutableList<T> = mutableListOf())
    : MutableList<T> by list, LiveData<List<T>>() {
    override fun add(element: T): Boolean {
        return element.actionAndUpdate { list.add(it) }

    }
    private fun <T> T.actionAndUpdate(action: (item: T) -> Boolean): Boolean =
        action(this).applyIfTrue { updateValue() }

    private fun Boolean.applyIfTrue(action: () -> Unit): Boolean {
        takeIf { it }?.run { action() }
        return this
    }

    private fun updateValue() {
        value = list
    }
}