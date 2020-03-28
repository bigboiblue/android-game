package com.kyle.last_man_standing.game

import android.content.res.Resources
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2

class Background(res: Resources, resourceID: Int, screenWidth: Int, screenHeight: Int): ImageEntity(res, resourceID, screenWidth, screenHeight, Vector2(0.0, 0.0)) {
    override fun update(gameData: GameData) {}
}