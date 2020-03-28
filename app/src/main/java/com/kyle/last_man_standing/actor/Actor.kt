package com.kyle.last_man_standing.actor

import android.content.res.Resources
import android.graphics.Canvas
import com.kyle.last_man_standing.game.GameData
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2

abstract class Actor(res: Resources, imageID: Int, scale: Float, speed: Double, maxHealth: Double, location: Vector2): ImageEntity(res, imageID, scale, location) {
    var speed = speed;
    val maxHealth = maxHealth;
    var health: Double = maxHealth * 1.0;
    val healthBar = HealthBar(res, health, scale * 1.5f);

    override fun update(gameData: GameData) {
        super.update(gameData)
        healthBar.health = this.health;
        healthBar.location = Vector2(location.x, location.y - image.height / 1.5);
        healthBar.update(gameData);
    }

    override fun draw(canvas: Canvas) {
        healthBar.draw(canvas)
        super.draw(canvas)
    }

    override fun shouldRemove(): Boolean {
        return this.health <= 0;
    }
}