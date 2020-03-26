package com.kyle.game

import android.content.res.Resources
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build


class Pistol(res: Resources, scale: Float) :
    Weapon(res, R.drawable.pistol, scale) {

    private val pistolSoundID = GameView.addSound(R.raw.pistol_shot);

    override var fireRate = 500;
    override var damage = 7.5

    override fun fire() {
        GameView.playSound(pistolSoundID, 1.0f, 1.0f, 1, 0, 1.0f)
        projectiles.add(Tracer(res, scale, location, rotation));
        projectiles.last().addOffset(offset);
    }
}