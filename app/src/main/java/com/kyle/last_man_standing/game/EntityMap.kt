package com.kyle.last_man_standing.game

import android.util.Log
import com.kyle.last_man_standing.actor.Enemy
import com.kyle.last_man_standing.core.Entity

/**
 * A HashMap of <String, Set> where
 * - String --- is the javaClass.name
 * - Set --- is a MutableList of entities
 * **/
class EntityMap: HashMap<String, MutableList<Entity>>() {
    fun put(value: Entity) {
        /* to get name of object use obj.javaClass.name
         *  to get name of class to Class::class.java.name */
        this.getOrPut(value.javaClass.name) { mutableListOf() }.add(value)
        if (value is Enemy) {
            Log.d("____", "Multiplier: ${value.healthMultiplier} --- Health ${value.health}");
        }
    }
}

