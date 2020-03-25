package com.kyle.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            //intent.putExtra("saveData", data);
            startActivity(intent)
        }

    }
}