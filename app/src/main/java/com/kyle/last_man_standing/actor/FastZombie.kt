package com.kyle.last_man_standing.actor

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2

class FastZombie(res: Resources, scale: Float, location: Vector2, healthMultiplier: Double): Enemy(res,
    R.drawable.zombie_fast, scale, 5.0, 12.0, location, healthMultiplier) {
    override var damage = 5.0;
    override var hitSpeed = 500L;
    override var attackSoundID= GameView.addSound(R.raw.fast_zombie_attack)
    override var powerupPercent = 30.0;


    init {
        this.location = location;
    }
}