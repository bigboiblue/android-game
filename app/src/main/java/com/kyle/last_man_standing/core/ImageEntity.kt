package com.kyle.last_man_standing.core

import android.content.res.Resources
import android.graphics.*
import com.kyle.last_man_standing.game.GameData
import mikera.vectorz.Vector2

abstract class ImageEntity(res: Resources, resourceID: Int, width: Int, height: Int, location: Vector2): Entity(location) {
    val res = res;

    var image: Bitmap;
    protected val paint = Paint();
    var scale = 1.0f
    var offset = Vector2(0.0, 0.0);
    var bounds = Polygon();

    constructor(res: Resources, resourceID: Int, size: Int, location: Vector2) : this(res, resourceID, size, size,location);
    constructor(res: Resources, resourceID: Int, scale: Float, location: Vector2) : this(res, resourceID, scale.toInt(), scale.toInt(), location) {
        this.scale = scale;
        val bitmapOptions = BitmapFactory.Options();
        bitmapOptions.inScaled = false; // Disable upscaling smoothing
        val bitmap = BitmapFactory.decodeResource(res, resourceID, bitmapOptions);
        image = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), false)
    }

    init {
        val bitmapOptions = BitmapFactory.Options();
        bitmapOptions.inScaled = false; // Disable upscaling smoothing
        val bitmap = BitmapFactory.decodeResource(res, resourceID, bitmapOptions);
        image = Bitmap.createScaledBitmap(bitmap, width, height, false)
    }


    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, matrix, paint);

//        drawBounds(canvas)
    }
    fun drawBounds(canvas: Canvas) {
        val newPaint = Paint();
        newPaint.color = (Color.RED)
        bounds.points.forEach {
            canvas.drawCircle(it.x.toFloat(), it.y.toFloat(), 6f,  newPaint)
        }
    }

    override fun update(gameData: GameData) { // TODO: Remove this implementation in unneeded entities for optimisation
        updateMatrix()
        updateBounds()
    }

    fun updateMatrix() {
        val midWidth = image.width / 2.0f;
        val midHeight = image.height / 2.0f

        matrix.reset();
        matrix.preTranslate(offset.x.toFloat(), -offset.y.toFloat());
        matrix.postRotate(rotation, midWidth, midHeight);
        // Minus half width and half height to get actual screen coordinates
        matrix.postTranslate(x.toFloat() - midWidth, y.toFloat() - midHeight);
    }



    fun addOffset(x: Double, y: Double) {
        offset = offset.addCopy(Vector2(x, y)) as Vector2;
    }
    fun addOffset(vec: Vector2) {
        offset = offset.addCopy(vec) as Vector2;
    }

    fun isTouching(other: ImageEntity): Boolean {
//        if(currentTimeMillis() - GameView.getGameStart() < 50) return false; // Stop initial damage glitch
        return bounds.isIntersecting(other.bounds);
    }

    fun locationAfterOffset() : Vector2 {
        return locationAfterOffset((image.width / 2.0f).toDouble(), (image.height / 2.0f).toDouble())
    }

    fun locationAfterOffset(x: Double, y: Double) : Vector2 {
        return locationAfterOffset(Vector2(x.toDouble(), y.toDouble()))
    }

    fun locationAfterOffset(point: Vector2) : Vector2 {
        val mappedPoints = floatArrayOf(point.x.toFloat(), point.y.toFloat());
        matrix.mapPoints(mappedPoints);
        return Vector2(mappedPoints[0].toDouble(), mappedPoints[1].toDouble());
    }

    fun updateBounds() {
        bounds = Polygon();
        bounds.points.add(locationAfterOffset(0.0, 0.0)) // Top Left
        bounds.points.add(locationAfterOffset(image.width.toDouble(), 0.0)) // Top Right
        bounds.points.add(locationAfterOffset(0.0, image.height.toDouble())) // Bottom Left
        bounds.points.add(locationAfterOffset(image.width.toDouble(), image.height.toDouble())) // Bottom Right
    }

}
