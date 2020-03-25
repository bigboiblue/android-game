package com.kyle.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.Log
import android.view.MotionEvent
import mikera.vectorz.Vector2
import java.lang.Math.cos
import java.lang.Math.toDegrees
import kotlin.math.acos

const val PLAYER_SCALE: Float = 3.2f; // Image scaling for player
const val CIRCLE_SIZE = 300;
class Player(res: Resources): ImageEntity(res, R.drawable.player, PLAYER_SCALE) { // Resources are the actual resources while R only gets the id

    private var circle = PlayerCircle(res, CIRCLE_SIZE);
    private var speed = 7; // Base speed
    private var weapon: Weapon = Pistol(res, PLAYER_SCALE.toFloat());

    init {
        weapon.addOffset(image.width / 5.3, image.height / 2.75);
    }

    override fun update(gameData: GameData) {
        if(gameData.isTouchInitialized() && gameData.lastTouch.action != MotionEvent.ACTION_UP){
            touchUpdate(gameData.lastTouch)
        };
        circle.update(gameData);
        weapon.update(gameData);

        super.update(gameData);
    }

    fun touchUpdate(lastTouch: MotionEvent) {
        val touchLocation = Vector2(lastTouch.x.toDouble(), lastTouch.y.toDouble());
        faceLocation(touchLocation);

        val difVec = touchLocation.subCopy(location) as Vector2;
        val difVecNorm = difVec.normaliseCopy() as Vector2;

        val isTouchInsideCircle = difVec.magnitude() < CIRCLE_SIZE / 2;
        if(!isTouchInsideCircle){
            x += difVecNorm.x * speed;
            y += difVecNorm.y * speed;
        }

        circle.location = this.location;
        weapon.location = this.location;
        weapon.rotation = this.rotation;
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas);
        circle.draw(canvas);
        weapon.draw(canvas);
    }

}