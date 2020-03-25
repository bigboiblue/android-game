package com.kyle.game

import android.content.res.Resources
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Weapon(res: Resources, resourceID: Int, scale: Float):
    ImageEntity(res, resourceID, scale) {

    var projectiles = mutableListOf<Projectile>()
    var lastShot = 0L;
    abstract var fireRate: Int; // Millis til next bullet

    override fun update(gameData: GameData) {
        super.update(gameData)
        var count = 0;

        if(gameData.isTouchInitialized() && gameData.lastTouch.action != MotionEvent.ACTION_UP) {
            val currentTime = currentTimeMillis()
            if(currentTime - lastShot >= fireRate){
                fire()
                lastShot = currentTime;
            }
        }

        while(count < projectiles.size){
            if(projectiles.get(count).shouldRemove()){
                projectiles.removeAt(count);
                continue;
            }
            projectiles.get(count).update(gameData);

            count++;
        }
    }

    abstract fun fire();

//    fun updateMatrix() {
//        val midWidth = image.width / 2.0f;
//        val midHeight = image.height / 2.0f
//        matrix.reset();
//        matrix.preTranslate(offset.x.toFloat(), -offset.y.toFloat()) // minus y because screen coords reversed
//        matrix.postRotate(rotation, midWidth, midHeight);
//        // Minus half width and half height to get actual screen coordinates
//        matrix.postTranslate(x.toFloat() - midWidth , y.toFloat() - midHeight);
//    }

    override fun draw(canvas: Canvas) {
        projectiles.forEach {
            it.draw(canvas);
        }
        super.draw(canvas)
    }

}