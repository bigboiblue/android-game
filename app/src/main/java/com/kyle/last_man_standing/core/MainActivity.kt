package com.kyle.last_man_standing.core

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.security.NetworkSecurityPolicy
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kyle.last_man_standing.R
import kotlinx.android.synthetic.main.activity_main.*
import com.github.kittinunf.result.Result;
import com.github.kittinunf.fuel.httpGet

import java.net.URL



class MainActivity : AppCompatActivity() {
    val ip = "167.86.81.124";
    val port = "28191"
    val url = "http://$ip:$port/api/v1/scores"
    var response = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        doRequest();
        NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        val mostRoundsText = findViewById<TextView>(R.id.mostRoundsText)
        val mostPointsText = findViewById<TextView>(R.id.mostPointsText)

        val prefs: SharedPreferences = this.getSharedPreferences("high_score", Context.MODE_PRIVATE)
        val mostRounds = prefs.getLong("most_rounds", 0L)
        val mostPoints = prefs.getLong("most_points", 0L)
pwd
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
        val httpAsync = url
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        runOnUiThread {
                            Log.d("__ERROR__", ex.toString());
                        }
                    }
                    is Result.Success -> {
                        val data = result.get()
                        runOnUiThread{
                            mostRoundsText.text = data.toString();
                        }
                    }
                }
            }

        httpAsync.join()
    }
}