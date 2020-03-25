package com.kyle.game;

import android.content.Context;
import android.graphics.*
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView;
import java.lang.System.currentTimeMillis
import kotlin.reflect.typeOf

/** SurfaceView not drawn on the UI thread **/
class GameView(context: Context, screenSize: Point): SurfaceView(context), Runnable{

    companion object {
        private var context: Context? = null
        private var soundPool: SoundPool? = null
        fun getAppContext(): Context? {
            return context;
        }

        /** Adds sound to SoundPool and Returns sound id **/
        fun addSound(resourceID: Int, priority: Int = 1): Int {
            return soundPool!!.load(context, resourceID, priority.toInt())
        }

        fun playSound(soundID: Int, leftVolume: Float, rightVolume: Float, priority: Int, loop: Int, rate: Float) {
            soundPool?.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    // No need to define target FPS
    // Android automatically locks the frame rate and max refresh rate

    private lateinit var thread: Thread;
    private var isPlaying = false;
    private var gameData = GameData();
    private var entities = gameData.entities; // References gameData's entities
    private var music = MediaPlayer.create(context, R.raw.beauty_of_annihilation);

    init {
        // Init static members
        GameView.context = context;
        initSoundPool();

        gameData.background = Background(resources, R.drawable.background, screenSize.x, screenSize.y);
        entities.put(Player(resources));
        entities.put(Enemy(resources, R.drawable.zombie_norm, 3.2f));
    }

    fun initSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()
            soundPool = SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(10, AudioManager.USE_DEFAULT_STREAM_TYPE, 1)
        }
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) gameData.lastTouch = event
        return super.onTouchEvent(event)
    }

    override fun run() {
        while(isPlaying) {
            update();
            draw();
        }
    }

    /** Update all entities **/
    fun update() {
        entities.forEach {
            it.value.forEach{ entity ->
                entity.update(gameData);
            }
        }
    }

    /** Draws background, and each entity to canvas **/
    fun draw() {
        if(!holder.surface.isValid) return; // Is surface initialised?

        var canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK); // Reset canvas
        gameData.background.draw(canvas);
        entities.forEach {
            it.value.forEach{ entity ->
                entity.draw(canvas);
            }
        }
        holder.unlockCanvasAndPost(canvas);
    }

    fun pause() {
        isPlaying = false;
        thread.join();
        music.pause();
    }

    fun resume() {
        isPlaying = true;
        thread = Thread(this);
        thread.start();
        music.isLooping = true;
        music.start();
    }

}
