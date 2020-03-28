package com.kyle.last_man_standing.actor

import android.content.res.Resources
import com.kyle.last_man_standing.core.GameView
import com.kyle.last_man_standing.game.GameData
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Enemy(res: Resources, imageResID: Int, scale: Float,  speed: Double, maxHealth: Double, location: Vector2, healthMultiplier: Double): Actor(res, imageResID, scale, speed, maxHealth * healthMultiplier, location) {

    abstract var damage: Double;
    abstract var hitSpeed: Long; // Number of millis between hits
    abstract var attackSoundID: Int;
    abstract var powerupPercent: Double; //Percent Chance of powerup
    val healthMultiplier = healthMultiplier;
    var lastAttack = 0L;

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

        var distanceMultiplier = 1.0;
        val distance = image.height + player.image.height / 2;
        if(difVec.magnitude() < distance){
            distanceMultiplier = -0.5 + (1.5 * (difVec.magnitude() / distance))
        }

        x += difVecNorm.x * speed * distanceMultiplier;
        y += difVecNorm.y * speed * distanceMultiplier;

        /** Player Collision handling **/
        val time = currentTimeMillis();
        if(isTouching(player) && time - lastAttack > hitSpeed) {
            player.health -= damage;
            GameView.playSound(attackSoundID, 0.8f, 0.8f, 1, 0, 1f )
            lastAttack = time;
            if(player.shouldRemove()){
                gameData.addToRemoveQueue(player);
            }
        }
    }
}