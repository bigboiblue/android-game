package com.kyle.last_man_standing.actor

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.game.GameData
import com.kyle.last_man_standing.core.ImageEntity
import mikera.vectorz.Vector2
import kotlin.math.max

class HealthBar(res: Resources, maxHealth: Double, scale: Float): ImageEntity(res,
    R.drawable.healthbar_red, scale, Vector2(0.0,0.0)) {

    val greenImage: Bitmap;
    var updatedGreenImage: Bitmap;
    val maxHealth = maxHealth * 1.0;
    var health = maxHealth * 1.0;
    var prevHealth = maxHealth;
    var fraction: Double = health / maxHealth * 100.0;

    init {
        val bitmapOptions = BitmapFactory.Options();
        bitmapOptions.inScaled = false; // Disable upscaling smoothing
        val bitmap = BitmapFactory.decodeResource(res,
            R.drawable.healthbar_green, bitmapOptions);
        greenImage = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), false)
        updatedGreenImage = Bitmap.createBitmap(greenImage);
    }

    override fun update(gameData: GameData) {
        updateGreenImagePixels()
        super.update(gameData);
    }

    /** Sets pixels to transparent equal to percentage health left (creating health loss effect) **/
    fun updateGreenImagePixels() {
        if(prevHealth != health){
            prevHealth = health;
            fraction =  max(health / maxHealth, 0.0);
            updatedGreenImage = Bitmap.createBitmap(greenImage);
            for(x in (image.width * fraction).toInt() until image.width) {
                for(y in 0 until image.height) {
                    updatedGreenImage.setPixel(x, y, 0x00000000) // Set pixel to transparent
                }
            }

        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(updatedGreenImage, matrix, paint);
    }
}