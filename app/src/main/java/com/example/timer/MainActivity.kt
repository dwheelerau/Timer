package com.example.timer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var seconds=0
    private var timerJob: Job?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startTimer()
        }

        binding.btnStop.setOnClickListener {
            pauseTimer()
        }

        binding.btnReset.setOnClickListener {
            resetTimer()
        }
    }
    fun startTimer(){
        if(timerJob==null || timerJob?.isActive==false){
            timerJob=lifecycleScope.launch{
                while (isActive){
                    delayOneSecond()
                    seconds++
                    updateTimer()
                }
            }
        }
    }
    suspend fun delayOneSecond(){
        delay(1000)
    }
    fun resetTimer() {
        timerJob?.cancel()
        seconds=0
        updateTimer()
    }
    fun updateTimer(){
        val minute=seconds/60
        val secs=seconds%60
        val timer=String.format("%02d:%02d",minute, secs)
        binding.tvTimer.text=timer
    }
    fun pauseTimer() {
        timerJob?.cancel()
    }
}