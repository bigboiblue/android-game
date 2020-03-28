package com.kyle.last_man_standing.powerup

import android.content.res.Resources
import com.kyle.last_man_standing.actor.Player
import com.kyle.last_man_standing.core.ImageEntity
import com.kyle.last_man_standing.game.GameData
import mikera.vectorz.Vector2

abstract class Powerup(res: Resources, imageID: Int, scale: Float, location: Vector2): ImageEntity(res, imageID, scale, location) {
    var givenPowerup = false;

    init {
        updateMatrix()
        updateBounds()
    }

    override fun update(gameData: GameData) {
        var player: Player? = null;
        gameData.entities.forEach {
            if(it.key == Player::class.java.name) player = it.value.get(0) as Player;
        }
        player?.let { playerUpdate(gameData, it) }

        super.update(gameData)
    }

        fun playerUpdate(gameData: GameData, player: Player) {
            if(isTouching(player) && !givenPowerup) {
                givenPowerup = true;
                givePowerup(player);
                gameData.addToRemoveQueue(this);
            }
        }

        abstract fun givePowerup(player: Player);

}