package com.kyle.game

import android.content.res.Resources

class NormalZombie(res: Resources, scale: Float): Enemy(res, R.drawable.zombie_norm, scale, 3.0, 20.0) {
    override var damage = 10.0;
    override var hitSpeed = 500L;
}