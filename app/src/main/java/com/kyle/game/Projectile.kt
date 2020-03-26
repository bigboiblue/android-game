package com.kyle.game

import android.content.res.Resources
import android.util.Log
import android.view.VelocityTracker
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Projectile(res: Resources, resourceID: Int, scale: Float, location: Vector2, rotation: Float) :
        ImageEntity(res, resourceID, scale) {

    var hitEntities = mutableListOf<ImageEntity>();

    init {
        this.location = location
        this.offset = Vector2(0.0, (image.height / 2).toDouble());
        this.rotation = rotation;
    }

    override fun update(gameData: GameData) {
        collisionUpdates(gameData);
        super.update(gameData)
    }

    inline fun collisionUpdates(gameData: GameData) {
        gameData.entities.forEach {
            it.value.forEach {
                if(isTouching(it as ImageEntity)){
                    hitEntities.add(it);
                }
            }
        }
    }


    abstract val LIFETIME: Long;
    protected val creationTime = currentTimeMillis();

    override fun shouldRemove(): Boolean {
        return (currentTimeMillis() - creationTime > LIFETIME);
    }
}
