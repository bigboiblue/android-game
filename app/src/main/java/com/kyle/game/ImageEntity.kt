package com.kyle.game

import android.content.res.Resources
import android.graphics.*
import android.renderscript.Matrix4f
import android.util.Log
import mikera.matrixx.Matrix22
import mikera.matrixx.Matrix33
import mikera.matrixx.Matrixx
import mikera.vectorz.Vector2
import kotlin.math.acos

abstract class ImageEntity(res: Resources, resourceID: Int, width: Int, height: Int): Entity() {
    protected val res = res;

    lateinit var image: Bitmap;
    protected val paint = Paint();
    protected var scale = 1.0f
    var offset: Vector2 = Vector2(0.0, 0.0);

    constructor(res: Resources, resourceID: Int, size: Int) : this(res, resourceID, size, size);
    constructor(res: Resources, resourceID: Int, scale: Float) : this(res, resourceID, scale.toInt(), scale.toInt()) {
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
    }

    override fun update(gameData: GameData) { // TODO: Remove this implementation in unneeded entities for optimisation
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
        return touchesImageEntity(this, other);
    }

    fun getLocationAfterTransform() : Vector2 {
        val floats = FloatArray(9);
        matrix.getValues(floats);
        var doubles = DoubleArray(9);
        for((index, float) in floats.withIndex()) doubles[index] = float.toDouble();
        var mat = Matrix22(doubles[0], doubles[1], doubles[2], doubles[3]);
        val ret = mat.multiplyCopy(location);
//        Log.d("dwadwa", "${Vector2(ret.get(0, 0), ret.get(1, 0))}")
//        Log.d("dwadwa", "${Vector2(ret.get(0, 0), ret.get(0, 1))}")
        return Vector2(ret.get(0, 0), ret.get(0, 1));
    }

}
