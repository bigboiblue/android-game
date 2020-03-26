package com.kyle.game

import android.content.res.Resources
import android.graphics.Canvas
import mikera.vectorz.Vector2

abstract class Actor(res: Resources, imageID: Int, scale: Float, speed: Double, maxHealth: Double): ImageEntity(res, imageID, scale) {
    protected var speed = speed;
    protected val maxHealth = maxHealth;
    var health: Double = maxHealth;
    val healthBar = HealthBar(res, maxHealth, scale * 1.5f);

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