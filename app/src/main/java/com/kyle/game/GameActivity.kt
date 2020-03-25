package com.kyle.game

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class GameActivity: AppCompatActivity() {
    lateinit var gameView: GameView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var screenSize = Point();
        windowManager.defaultDisplay.getSize(screenSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.defaultDisplay.getRealSize(screenSize) // Strange screen ratio fixes
        };
        gameView = GameView(this, screenSize);
        setContentView(gameView);
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