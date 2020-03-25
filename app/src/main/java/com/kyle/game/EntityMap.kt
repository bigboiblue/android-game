package com.kyle.game

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
    }
}

