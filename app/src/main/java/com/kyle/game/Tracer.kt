package com.kyle.game

import android.content.res.Resources
import mikera.vectorz.Vector2

class Tracer(res: Resources, scale: Float, location: Vector2, rotation: Float): Projectile(res, R.drawable.tracer, scale, location, rotation) {
    override val LIFETIME: Long = 1000 / 60 * 1; // Alive for 3 frames
}