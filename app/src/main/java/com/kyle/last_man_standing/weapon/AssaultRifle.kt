package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2


class AssaultRifle(res: Resources, scale: Float, location: Vector2) :
    Weapon(res,
        R.drawable.assault_rifle, scale, location) {

    private val arSoundID = GameView.addSound(R.raw.assault_rifle_shot);

    override var fireRate = 75;
    override val damage = 4.0

    override fun fire() {
        GameView.playSound(arSoundID, 0.8f, 0.8f, 1, 0, 1.0f)
        projectiles.add(
            Tracer(
                res,
                scale,
                location,
                rotation,
                3
            )
        );
        projectiles.last().addOffset(offset);
    }
}