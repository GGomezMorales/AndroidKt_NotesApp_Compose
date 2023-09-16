package com.ggomezmorales.notesapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private var sharePreferences : SharedPreferences? = null

    private val _titleState = MutableStateFlow("")
    val titleState = _titleState.asStateFlow()

    private val _descriptionState = MutableStateFlow("")
    val descriptionState = _descriptionState.asStateFlow()

    fun loadData() {
        val title = sharePreferences?.getString("title", "").orEmpty()
        _titleState.value = title

        val description = sharePreferences?.getString("description", "").orEmpty()
        _descriptionState.value = description
    }

    fun updateTitle(title: String) {
        _titleState.value = title
    }

    fun updateDescription(description: String) {
        _descriptionState.value = description
    }

    fun saveData() {
        sharePreferences?.edit()?.putString("title", titleState.value)?.apply()
        sharePreferences?.edit()?.putString("description", descriptionState.value)?.apply()
    }

    fun initSharePreferences(sharedPreferences: SharedPreferences){
        sharePreferences = sharedPreferences
    }
}