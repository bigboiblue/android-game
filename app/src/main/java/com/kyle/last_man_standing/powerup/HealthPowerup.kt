package com.kyle.last_man_standing.powerup

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.actor.Player
import mikera.vectorz.Vector2

class HealthPowerup(res: Resources, scale: Float, location: Vector2): Powerup(res, R.drawable.health_powerup, scale, location){
    override fun givePowerup(player: Player) {
        player.health = player.maxHealth * 1.0;
    }
}
