package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timeTextView: TextView
    private lateinit var cancelBtn: Button
    private lateinit var playBtn: ImageButton
    private lateinit var seekbar: SeekBar

    private var time = 0
    private var isPause: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeTextView = findViewById(R.id.timeTextView)
        cancelBtn = findViewById(R.id.cancelBtn)
        playBtn = findViewById(R.id.playBtn)
        seekbar = findViewById(R.id.seekBar)

        isPause = savedInstanceState?.getBoolean("isPause",false)?:false
        time = savedInstanceState?.getInt("time",0)?:0

        seekbar.progress = time

        if (isPause) {
            playBtn.setImageResource(R.drawable.ic_play_arrow_black_40dp)
        } else {
            playBtn.setImageResource(R.drawable.ic_pause_black_40dp)
        }
        if(time > 0){
            calTime()
        }



        playBtn.setOnClickListener(View.OnClickListener {
            isPause = !isPause
            if (isPause) {
                playBtn.setImageResource(R.drawable.ic_play_arrow_black_40dp)
            } else {
                playBtn.setImageResource(R.drawable.ic_pause_black_40dp)
            }
        })

        cancelBtn.setOnClickListener(View.OnClickListener {
            isPause = true
            time = 0
            playBtn.setImageResource(R.drawable.ic_play_arrow_black_40dp)
            seekbar.progress = 0
            calTime()
        })

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    time = seekBar.progress
                    calTime()
                }
            }

        })
        timer()
    }

    private fun timer() {
        val handler: Handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                if (time >= 0 && !isPause) {

                    calTime()
                    time--;
                    seekbar.progress = time

                    handler.postDelayed(this, 1000)
                } else {
                    playBtn.setImageResource(R.drawable.ic_play_arrow_black_40dp)
                    isPause = true
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun calTime() {
        var hours = time / 3600
        var min = (time % 3600) / 60
        var sec = time % 60
        var displayString =
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec)
        timeTextView.text = displayString
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean("isPause",isPause)
        savedInstanceState.putInt("time",time)
    }

}
