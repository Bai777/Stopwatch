package com.example.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.data.TimestampProvider
import com.example.stopwatch.databinding.ActivityMainBinding
import com.example.stopwatch.datasource.StopwatchStateCalculator
import com.example.stopwatch.repository.ElapsedTimeCalculator
import com.example.stopwatch.repository.StopwatchStateHolder
import com.example.stopwatch.repository.TimestampMillisecondsFormatter
import com.example.stopwatch.repository.stateflow.StopwatchListOrchestrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stopWatcherDisplay()
        startStopWatcher()
        pauseStopWatcher()
        stopStopWatcher()
    }

    private fun startStopWatcher() {
        binding.buttonStart.setOnClickListener {
            stopwatchListOrchestrator.start()
        }
    }

    private fun pauseStopWatcher() {
        binding.buttonPause.setOnClickListener {
            stopwatchListOrchestrator.pause()
        }
    }

    private fun stopStopWatcher() {
        binding.buttonStop.setOnClickListener {
            stopwatchListOrchestrator.stop()
        }
    }

    private fun stopWatcherDisplay() {
        CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                binding.textTime.text = it
            }
        }
    }
}