package com.kyle.game

import android.content.res.Resources
import android.view.MotionEvent

/** A structure that holds all gameData **/
class GameData() {
    var entities = EntityMap(); // Entity Map is a HashMultiMap ( HashMap< String, MutableSet<Entity> > )
    lateinit var background: Background;
    lateinit var lastTouch: MotionEvent;

    fun isTouchInitialized(): Boolean {
        return ::lastTouch.isInitialized;
    }
}