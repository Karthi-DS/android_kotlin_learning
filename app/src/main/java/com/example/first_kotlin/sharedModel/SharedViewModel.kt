package com.example.first_kotlin.sharedModel

import androidx.lifecycle.ViewModel
import com.example.first_kotlin.model.FormData

class SharedViewModel: ViewModel() {

    val formData = FormData()
}