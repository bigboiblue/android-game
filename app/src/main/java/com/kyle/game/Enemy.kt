package com.kyle.game

import android.content.res.Resources
import android.util.Log
import mikera.vectorz.Vector2

class Enemy(res: Resources, imageResID: Int, scale: Float): ImageEntity(res, imageResID, scale) {
    private var speed = 3; // Base speed


    init {
        location = Vector2(100.0, 100.0);
    }

    override fun update(gameData: GameData) {
        var player: Player? = null;
        gameData.entities.forEach {
            if(it.key == Player::class.java.name) player = it.value.get(0) as Player;
        }
        faceLocation(player!!.location);

        val difVec = player!!.location.subCopy(location) as Vector2;
        val difVecNorm = difVec.normaliseCopy() as Vector2;

        x += difVecNorm.x * speed;
        y += difVecNorm.y * speed;

        if(isTouching(player!!)) {
            Log.d("____", "TOUCIG")
        }

        super.update(gameData)
    }
}