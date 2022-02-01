package com.example.stopwatch.repository

import com.example.stopwatch.data.DataDTO
import com.example.stopwatch.datasource.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class Repository(dataSource: DataSource = DataSource()) {
    val userData: Flow<DataDTO> =
        dataSource.data.map { data ->
            DataDTO(data)
        }
}