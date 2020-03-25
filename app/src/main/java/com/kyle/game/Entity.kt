package com.kyle.game

import android.graphics.Canvas
import android.graphics.Matrix
import mikera.vectorz.Vector2
import kotlin.math.acos

abstract class Entity {
    var location = Vector2(100.0, 100.0); // Center location
    var rotation: Float = 0.0f;
    protected var matrix = Matrix();

    /** x and y getters / setters **/
    var x: Double
        get() = location.x
        set(x: Double) { location.x = x }
    var y: Double
        get() = location.y
        set(y: Double) { location.y = y }

    fun faceLocation(loc: Vector2) {
        val difVec = loc.subCopy(location) as Vector2 // Normalised difference vector
        val difVecNorm = difVec.normaliseCopy() as Vector2;
        val rotVec = Vector2(difVecNorm.x, difVecNorm.y* -1); // Multiply Y by -1 because screen coordinates inverted
        val upVec = Vector2(0.0, 1.0); // Normalise vec pointing up (need to find angle between the two
        rotation = Math.toDegrees(acos(rotVec.dotProduct(upVec))).toFloat();
        if(difVec.x < upVec.x) rotation =  (360 - rotation);
    }

    abstract fun update(gameData: GameData);
    abstract fun draw(canvas: Canvas);
    open fun shouldRemove(): Boolean { return false };
}