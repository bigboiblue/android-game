package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import com.kyle.last_man_standing.R
import mikera.vectorz.Vector2

class Tracer(res: Resources, scale: Float, location: Vector2, rotation: Float, numFramesAlive: Int = 1): Projectile(res,
    R.drawable.tracer, scale, location, rotation) {
    override val LIFETIME: Long = 1000 / 60 * numFramesAlive.toLong(); // Alive for numFramesAlive frames
}