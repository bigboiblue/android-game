package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2


class Pistol(res: Resources, scale: Float, location: Vector2) :
    Weapon(res,
        R.drawable.pistol, scale, location) {

    private val pistolSoundID = GameView.addSound(R.raw.pistol_shot);

    override var fireRate = 500;
    override var damage = 10.0

    override fun fire() {
        GameView.playSound(pistolSoundID, 1.0f, 1.0f, 1, 0, 1.0f)
        projectiles.add(
            Tracer(
                res,
                scale,
                location,
                rotation,
                2
            )
        );
        projectiles.last().addOffset(offset);
    }
}