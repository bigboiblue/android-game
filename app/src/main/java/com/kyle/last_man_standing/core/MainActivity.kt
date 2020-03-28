package com.kyle.last_man_standing.core

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kyle.last_man_standing.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL



class MainActivity : AppCompatActivity() {
    val ip = "10.0.2.2";
    val port = "28191"
    val url = "http://$ip:$port/api/v1/scores"

    override fun onCreate(savedInstanceState: Bundle?) {
        doRequest();

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        val mostRoundsText = findViewById<TextView>(R.id.mostRoundsText)
        val mostPointsText = findViewById<TextView>(R.id.mostPointsText)

        val prefs: SharedPreferences = this.getSharedPreferences("high_score", Context.MODE_PRIVATE)
        val mostRounds = prefs.getLong("most_rounds", 0L)
        val mostPoints = prefs.getLong("most_points", 0L)

        mostRoundsText.text = "Most Rounds: $mostRounds";
        mostPointsText.text = "Most Points: $mostPoints";

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            //intent.putExtra("saveData", data);
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            this.finishAffinity();
        }

    }
    fun printStuff() {
        Log.d("dwadw", "DWadwadaw")

    }

    fun doRequest() {
        doAsync{
            val result = URL(url).readText()
            uiThread {
                Log.d("______Request_____", result)
                longToast("Request performed")
            }
            Log.d("______Request_____", result)

        }

    }
}