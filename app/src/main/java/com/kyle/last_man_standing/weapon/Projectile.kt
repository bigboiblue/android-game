package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import com.kyle.last_man_standing.game.GameData
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Projectile(res: Resources, resourceID: Int, scale: Float, location: Vector2, rotation: Float) :
        ImageEntity(res, resourceID, scale, location) {

    var hitEntities = mutableListOf<ImageEntity>();
    var hasHit = false;

    init {
        this.location = location
        this.offset = Vector2(0.0, (image.height / 2).toDouble());
        this.rotation = rotation;
    }

    override fun update(gameData: GameData) {
        updateBounds();
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
