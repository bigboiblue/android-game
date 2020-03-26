package com.kyle.game

import android.content.res.Resources
import android.util.Log
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Enemy(res: Resources, imageResID: Int, scale: Float,  speed: Double, maxHealth: Double): Actor(res, imageResID, scale, speed, maxHealth) {

    abstract var damage: Double;
    abstract var hitSpeed: Long; // Number of millis between hits
    var lastAttack = 0L;

    init {
        location = Vector2(100.0, 100.0);
    }

    override fun update(gameData: GameData) {
        /** Follow Player **/
        var player: Player? = null;
        gameData.entities.forEach {
            if(it.key == Player::class.java.name) player = it.value.get(0) as Player;
        }
        player?.let { playerUpdate(gameData, it) }


        super.update(gameData)
    }

    fun playerUpdate(gameData: GameData, player: Player) {
        faceLocation(player.location);

        val difVec = player.location.subCopy(location) as Vector2;
        val difVecNorm = difVec.normaliseCopy() as Vector2;

        x += difVecNorm.x * speed;
        y += difVecNorm.y * speed;

        /** Player Collision handling **/
        val time = currentTimeMillis();
        if(isTouching(player) && time - lastAttack > hitSpeed) {
            player.health -= damage;
            lastAttack = time;
            Log.d("____", "Player Health: ${player.health}")
            if(player.shouldRemove()){
                gameData.addToRemoveQueue(player);
            }
        }
    }
}