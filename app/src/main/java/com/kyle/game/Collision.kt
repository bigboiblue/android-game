package com.kyle.game

import android.graphics.Bitmap
import android.media.Image

fun touchesImageEntity(e1: ImageEntity, e2: ImageEntity): Boolean {
    val b1 = e1.image;
    val b2 = e2.image;

    val xshift: Int = (e2.x - e1.x).toInt()
    val yshift: Int = (e2.y - e1.y).toInt()
    //Test if the Sprites overlap at all
    if (xshift > 0 && xshift > b1.width|| xshift < 0 && -xshift > b2.width) {
        return false
    }
    if (yshift > 0 && yshift > b1.height || yshift < 0 && -yshift > b2.height) {
        return false
    }
    //if they overlap, find out in which regions they do
    val leftx: Int
    val rightx: Int
    val topy: Int
    val bottomy: Int
    val leftx2: Int
    val topy2: Int
    if (xshift >= 0) {
        leftx = xshift
        leftx2 = 0
        rightx = Math.min(b1.width, b2.width + xshift)
    } else {
        rightx = Math.min(b1.width, b2.width + xshift)
        leftx = 0
        leftx2 = -xshift
    }
    if (yshift >= 0) {
        topy = yshift
        topy2 = 0
        bottomy = Math.min(b1.height, b2.height + yshift)
    } else {
        bottomy = Math.min(b1.height, b2.height + yshift)
        topy = 0
        topy2 = -yshift
    }
    //then compare the overlapping regions,
//if in any spot both pixels are not transparent, return true
    val ys = bottomy - topy
    val xs = rightx - leftx
    for (x in 0 until xs) {
        for (y in 0 until ys) {
            val pxl = b1.getPixel(leftx + x, topy + y)
            val pxl2 = b2.getPixel(leftx2 + x, topy2 + y)
            if (pxl and -0x1000000 != 0x0 && pxl2 and -0x1000000 != 0x0) {
                return true
            }
        }
    }
    return false
}