package com.kyle.game

import android.content.res.Resources
import android.view.MotionEvent

/** A structure that holds all gameData **/
class GameData() {
    var entities = EntityMap(); // Entity Map is a HashMultiMap ( HashMap< String, MutableSet<Entity> > )
    lateinit var background: Background;
    lateinit var lastTouch: MotionEvent;
    var removeQueue = mutableListOf<Pair<String, Int>>(); // Key Index pair

    fun isTouchInitialized(): Boolean {
        return ::lastTouch.isInitialized;
    }


    fun removeQueuedEntities() {
        removeQueue.forEach {
            if(entities.get(it.first)!!.size == 1) entities.remove(it.first)
            else entities.get(it.first)!!.removeAt(it.second);
        }
        removeQueue.clear();
    }

    fun addToRemoveQueue(entity: Entity) {
        for((key, value) in entities) {
            for((index, e) in value.withIndex()) {
                if(e == entity) {
                    removeQueue.add(Pair(key, index));
                }
            }
        }
    }
}