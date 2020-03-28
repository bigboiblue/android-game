package com.kyle.last_man_standing.actor

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2

class TankZombie(res: Resources, scale: Float, location: Vector2, healthMultiplier: Double): Enemy(res,
    R.drawable.zombie_tank, scale, 2.0, 125.0, location, healthMultiplier) {
    override var damage = 30.0;
    override var hitSpeed = 3000L;
    override var attackSoundID= GameView.addSound(R.raw.tank_zombie_attack)
    override var powerupPercent = 40.0;


    init {
        this.location = location;
    }
}