package com.example.stopwatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        throwError()
    }

    private fun throwError() {
        CoroutineScope(Dispatchers.Main).launch {
            (1..5).asFlow()
                .map {
                    //выбрасывается ошибка, если значение == 3
                    check(it != 3) { "Значение == $it" }//текст ошибки
                    it * it
                }
                .onCompletion {
                    Log.d(TAG, "onCompletion")
                }
                .catch { e-> Log.d(TAG, "Ошибка: $e") }
                .collect {
                    Log.d(TAG, it.toString())
                }

        }
    }

        companion object {
        private const val TAG = "###"
    }

}