package com.kyle.last_man_standing.weapon

import android.content.res.Resources
import android.graphics.Canvas
import android.view.MotionEvent
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.actor.Enemy
import com.kyle.last_man_standing.core.GameView
import com.kyle.last_man_standing.game.GameData
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

abstract class Weapon(res: Resources, resourceID: Int, scale: Float, location: Vector2):
    ImageEntity(res, resourceID, scale, location) {

    var projectiles = mutableListOf<Projectile>()
    var lastShot = 0L;
    abstract var fireRate: Int; // Millis til next bullet
    abstract val damage: Double;
    var hitSoundID = GameView.addSound(R.raw.hit_sound);

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
            projectiles[count].update(gameData);
            updateHitEntities(gameData , projectiles[count]);

            if(projectiles[count].shouldRemove()){
                projectiles.removeAt(count);
                continue;
            }
            count++;
        }
    }

    fun updateHitEntities(gameData: GameData, projectile: Projectile) {
        var hit = false;
        projectile.hitEntities.forEach {
            if(!projectile.hasHit && it is Enemy) {
                hit = true;
                it.health = it.health - damage;
                gameData.points += 10;
                GameView.playSound(hitSoundID, 0.75f, 0.75f, 1, 0, 1.0f);
                if(it.shouldRemove()){
                    gameData.addToRemoveQueue(it);
                }
            }
        }
        if(hit) projectile.hasHit = true; // Prevent double hit bug
    }

    abstract fun fire();

    override fun draw(canvas: Canvas) {
        projectiles.forEach {
            it.draw(canvas);
        }
        super.draw(canvas)
    }

}