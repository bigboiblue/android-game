package com.kyle.last_man_standing.core

import mikera.vectorz.Vector2

class Polygon(vararg points: Vector2) {
    var points =  mutableListOf<Vector2>()
        get() = field;


    init {
        points.forEach {
            this.points.add(it);
        }
    }


    fun isIntersecting(b: Polygon): Boolean {
        for (x in 0..1) {
            val polygon: Polygon = if (x == 0) this else b
            for (i1 in 0 until polygon.points.size) {
                val i2: Int = (i1 + 1) % polygon.points.size
                val p1: Vector2 = polygon.points.get(i1)
                val p2: Vector2 = polygon.points.get(i2)
                val normal = Vector2(p2.y - p1.y, p1.x - p2.x)
                var minA = Double.POSITIVE_INFINITY
                var maxA = Double.NEGATIVE_INFINITY
                for (p in this.points) {
                    val projected: Double = normal.x * p.x + normal.y * p.y
                    if (projected < minA) minA = projected
                    if (projected > maxA) maxA = projected
                }
                var minB = Double.POSITIVE_INFINITY
                var maxB = Double.NEGATIVE_INFINITY
                for (p in b.points) {
                    val projected: Double = normal.x * p.x + normal.y * p.y
                    if (projected < minB) minB = projected
                    if (projected > maxB) maxB = projected
                }
                if (maxA < minB || maxB < minA) return false
            }
        }
        return true
    }
}