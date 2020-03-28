package com.kyle.last_man_standing.actor

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2

class CrawlerZombie(res: Resources, scale: Float, location: Vector2, healthMultiplier: Double): Enemy(res,
    R.drawable.zombie_crawler, scale, 2.0, 30.0, location, healthMultiplier) {
    override var damage = 10.0;
    override var hitSpeed = 2000L;
    override var attackSoundID= GameView.addSound(R.raw.normal_zombie_attack)
    override var powerupPercent = 15.0;

    init {
        this.location = location;
    }
}