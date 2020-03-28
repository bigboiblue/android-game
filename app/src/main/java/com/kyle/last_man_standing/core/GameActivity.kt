package com.kyle.last_man_standing.core

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.kyle.last_man_standing.R
import mikera.vectorz.Vector2


class GameActivity: AppCompatActivity() {
    lateinit var gameView: GameView;
    lateinit var frameLayout: FrameLayout;
    lateinit var overlay: ConstraintLayout;
    lateinit var submitButton: Button;
    lateinit var textEdit: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var screenSize = Point();
        windowManager.defaultDisplay.getSize(screenSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.defaultDisplay.getSize(screenSize) // Strange screen ratio fixes
        };

        setContentView(R.layout.overlay);
        overlay = findViewById<ConstraintLayout>(R.id.overlay);
        submitButton = findViewById<Button>(R.id.submitButton);
        textEdit = findViewById<EditText>(R.id.editTextName);
        setContentView(R.layout.activity_game);
        gameView = GameView( this, Vector2(screenSize.x.toDouble(), screenSize.y.toDouble()));

        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.addView(gameView);




    }

    fun addEditText() {

        runOnUiThread {
            frameLayout.addView(overlay);
        }

    }

    override fun onResume() {
        super.onResume();
        if(::gameView.isInitialized) gameView.resume();
    }

    override fun onPause() {
        super.onPause();
        if(::gameView.isInitialized) gameView.pause();
    }

}