package com.kyle.last_man_standing.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.game.Score
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val ip = "bitdev.bar";
    val url = "https://www.$ip/api/v1/scores"

    @SuppressLint("SetTextI18n")
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
            intent.putExtra("url", url);
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            this.finishAffinity();
        }

    }

    @SuppressLint("SetTextI18n")
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
                        runOnUiThread {
                            val result: List<Score>? = Klaxon().parseArray<Score>(data)
                            if (result != null) createTable(result);
                        }
                    }
                }
            }

        httpAsync.join()
    }

    fun createTable(result: List<Score>) {
        val table = findViewById<TableLayout>(R.id.table)

        for((index, score) in result.withIndex()) {
            val param =
                TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
                )

            val row = TableRow(this);
            val name = TextView(this);
            val rounds = TextView(this);
            val points = TextView(this);

            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
            rounds.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
            points.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);

            name.setTextColor(0xdddddddd.toInt());
            rounds.setTextColor(0xdddddddd.toInt());
            points.setTextColor(0xdddddddd.toInt());

            val typeface = ResourcesCompat.getFont(this, R.font.minecraftia);

            name.typeface = typeface;
            rounds.typeface = typeface;
            points.typeface = typeface;

            name.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL;
            rounds.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL;
            points.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL;

            name.layoutParams = param;
            rounds.layoutParams = param;
            points.layoutParams = param;

            name.text = score.name
            rounds.text = score.rounds.toString();
            points.text = score.points.toString();

            row.addView(name);
            row.addView(rounds);
            row.addView(points);

            table.addView(row);
        }
    }
}