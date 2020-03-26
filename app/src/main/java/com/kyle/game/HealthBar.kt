package com.kyle.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import kotlin.math.max

class HealthBar(res: Resources, maxHealth: Double, scale: Float): ImageEntity(res, R.drawable.healthbar_red, scale) {

    val greenImage: Bitmap;
    var updatedGreenImage: Bitmap;
    val maxHealth = maxHealth;
    var health = maxHealth;
    var prevHealth = maxHealth;
    var fraction: Double = health / maxHealth * 100.0;

    init {
        val bitmapOptions = BitmapFactory.Options();
        bitmapOptions.inScaled = false; // Disable upscaling smoothing
        val bitmap = BitmapFactory.decodeResource(res,  R.drawable.healthbar_green, bitmapOptions);
        greenImage = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), false)
        updatedGreenImage = greenImage
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
            updatedGreenImage = greenImage;
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