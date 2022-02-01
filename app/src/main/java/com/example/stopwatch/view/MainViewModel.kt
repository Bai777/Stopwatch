package com.example.stopwatch.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stopwatch.data.DataDTO
import com.example.stopwatch.repository.Repository

class MainViewModel private constructor(
    repository: Repository = Repository(),
) : ViewModel() {
    internal val liveData: LiveData<DataDTO> = repository.userData.asLiveData()
}