package com.kyle.last_man_standing.game

import SpeedPowerup
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.media.MediaPlayer
import android.view.MotionEvent
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.actor.*
import com.kyle.last_man_standing.core.Entity
import com.kyle.last_man_standing.core.GameActivity
import com.kyle.last_man_standing.core.MainActivity
import com.kyle.last_man_standing.powerup.HealthPowerup
import com.kyle.last_man_standing.powerup.WeaponPowerup
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis
import java.util.*
import kotlin.math.min


/** A structure that holds all gameData **/
class GameData(context: GameActivity) {
    var entities = EntityMap(); // Entity Map is a HashMultiMap ( HashMap< String, MutableSet<Entity> > )
    lateinit var background: Background;
    lateinit var lastTouch: MotionEvent;
    var removeQueue = mutableListOf<Pair<String, Entity>>(); // Key Index pair
    val context = context;


    var roundSound = MediaPlayer.create(context, R.raw.new_round);

    var lastSpawn = System.currentTimeMillis();

    var currentNumEnemies = 0L;

    var spawnRate = 4000L; // Spawns every 4 seconds
    var baseNumEnemies = 5.0;
    val MAX_NUM_ENEMIES = 20;

    var healthMultiplier = 1.0;
    var numEnemiesMultiplier = 1.0;
    var spawnRateDivider = 1.0;

    var spawnedEnemiesThisRound = 0;
    var currentRound = 1L;

    var lastRoundStart = 0L;

    var points = 0L;

    fun shouldSpawnEnemy(): Boolean {
        val time = currentTimeMillis();
        val roundJustStarted = time - lastRoundStart < 7500;
        val canSpawn = time - lastSpawn > (spawnRate / spawnRateDivider)
        val maxNumEnemiesSpawned = spawnedEnemiesThisRound >= (baseNumEnemies * numEnemiesMultiplier);
        val maxOnField = currentNumEnemies >= MAX_NUM_ENEMIES;
        if(!roundJustStarted && canSpawn && !maxNumEnemiesSpawned && !maxNumEnemiesSpawned && !maxOnField) {
            return true
        }
        return false
    }

    fun spawnEnemy(res: Resources, screenSize: Vector2) {
        var hasSpawned = false;
        while(!hasSpawned){
            val num = Random().nextInt(20);
            when(num){
                in 0..4 -> {
                    entities.put(CrawlerZombie(res,3.2f,getRandomCorner(screenSize),healthMultiplier))
                    hasSpawned = true;
                };
                in 5..14 -> {
                    entities.put(NormalZombie(res,3.2f,getRandomCorner(screenSize),healthMultiplier))
                    hasSpawned = true;
                };
                in 15..17 -> {
                    if(currentRound >= 3){
                        entities.put(FastZombie(res,2f,getRandomCorner(screenSize),healthMultiplier))
                        hasSpawned = true;
                    }
                };
                in 18..19 -> {
                    if(currentRound >= 5){
                        entities.put(TankZombie(res,4f,getRandomCorner(screenSize),healthMultiplier))
                        hasSpawned = true;
                    }
                };
            }
        }
        lastSpawn = currentTimeMillis();
        currentNumEnemies++;
        spawnedEnemiesThisRound++;
    }


    fun getRandomCorner(screenSize: Vector2): Vector2 {
        val num = Random().nextInt(4);
        val offset = 350.0
        return when(num){
            0 -> Vector2(-offset, -offset); // Top left
            1 -> Vector2(screenSize.x + offset, -offset); // Top Right
            2 -> Vector2(-offset, screenSize.y + offset); // Bottom left
            else -> Vector2(screenSize.x + offset, screenSize.y + offset); // Bottom right
        }
    }


    fun isTouchInitialized(): Boolean {
        return ::lastTouch.isInitialized;
    }

    fun checkNextRound() {
        if(currentNumEnemies == 0L && spawnedEnemiesThisRound >= (baseNumEnemies * numEnemiesMultiplier).toLong()) {
            currentRound++;
            spawnedEnemiesThisRound = 0;
            numEnemiesMultiplier += 0.2;
            spawnRateDivider = min(spawnRateDivider + 0.3, 15.0)
            healthMultiplier = min(healthMultiplier + 0.2, 3.0)
            roundSound.setVolume(1.2f, 1.2f);
            roundSound.start();
            lastRoundStart = currentTimeMillis();
        }
    }


    fun removeQueuedEntities() {
        removeQueue.forEach {
            if(entities.get(it.first) != null){
                if(it.second is Enemy){
                    zombieDeath(it.second as Enemy);
                };
                if(it.second is Player){
                    endGame();
                }
                entities.get(it.first)!!.remove(it.second)
                if(entities[it.first]!!.size <= 0) entities.remove(it.first)
            }

        }
        removeQueue.clear();
    }

    fun endGame() {
        context.submitButton.setOnClickListener {
            val name: String  = context.textEdit.text.toString();
            val pair = getHighScore();
            val mostRounds = pair.first;
            val mostPoints = pair.second;
            if(currentRound > mostRounds || (currentRound == mostRounds && this.points > mostPoints)){
                saveHighScore(name);
            }
//            saveOnlineScore(name, currentRound, points);
            val intent = Intent(context, MainActivity::class.java);
            context.startActivity(intent);
        }
        context.addEditText();

    }

    fun getHighScore(): Pair<Long, Long> {
        val prefs: SharedPreferences = context.getSharedPreferences("high_score", Context.MODE_PRIVATE)
        return Pair(prefs.getLong("most_rounds", 0L),
            prefs.getLong("most_points", 0L))

    }

    fun saveHighScore(name: String) {
        val prefs: SharedPreferences = context.getSharedPreferences("high_score", Context.MODE_PRIVATE);
        val editor: SharedPreferences.Editor = prefs.edit();
        editor.putString("name", name);
        editor.putLong("most_rounds", currentRound);
        editor.putLong("most_points", points);
        editor.apply();
    }



    fun zombieDeath(enemy: Enemy) {
        points += 90L;
        currentNumEnemies--;
        checkNextRound();
        spawnPowerup(enemy);
    }

    fun spawnPowerup(enemy: Enemy) {
        val rand = Random();
        val num = rand.nextDouble() * 100.0;
        if(num <= enemy.powerupPercent * 2.0) {
            val int = rand.nextInt(4)
            when(int){
                0,1 -> entities.put(WeaponPowerup(enemy.res, 3.2f, enemy.location));
                2 -> entities.put(HealthPowerup(enemy.res, 3.2f, enemy.location));
                else -> entities.put(SpeedPowerup(enemy.res, 3.2f, enemy.location));
            }
        }
    }

    fun addToRemoveQueue(entity: Entity) {
        for((key, value) in entities) {
            for((index, e) in value.withIndex()) {
                if(e == entity) {
                    removeQueue.add(Pair(key, entity));
                }
            }
        }
    }
}