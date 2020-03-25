package com.kyle.game

import android.content.res.Resources

class Background(res: Resources, resourceID: Int, screenWidth: Int, screenHeight: Int): ImageEntity(res, resourceID, screenWidth, screenHeight) {
    override fun update(gameData: GameData) {}
}