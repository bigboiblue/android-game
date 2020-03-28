package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.GameView
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis
import java.util.*


class Minigun(res: Resources, scale: Float, location: Vector2) :
    Weapon(res,
        R.drawable.minigun, scale, location) {

    private val minigunSoundID = GameView.addSound(R.raw.minigun_sound);

    override var fireRate = 20;
    override val damage = 2.0

    var lastSound = 0L;

    override fun fire() {
        val time = currentTimeMillis();
        if(time - lastSound > 150) {
            GameView.playSound(minigunSoundID, 1.0f, 1.0f, 1, 0, 1.0f)
            lastSound = time;
        }
        projectiles.add(
            Tracer(
                res,
                scale,
                location,
                rotation + (Random().nextInt(9) - 4),
                3
            )
        );
        projectiles.last().addOffset(offset);
    }
}