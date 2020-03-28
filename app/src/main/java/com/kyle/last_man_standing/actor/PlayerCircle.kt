package com.kyle.last_man_standing.actor

import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2

class PlayerCircle(res: Resources, size: Int, location: Vector2): ImageEntity(res,
    R.drawable.dashed_circle, size, location) {
    init {
        this.location = location;
    }

}