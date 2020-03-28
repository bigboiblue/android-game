package com.kyle.last_man_standing.core;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.core.content.res.ResourcesCompat
import com.kyle.last_man_standing.actor.Player
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.game.Background
import com.kyle.last_man_standing.game.EntityMap
import com.kyle.last_man_standing.game.GameData
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis


/** SurfaceView not drawn on the UI thread **/
class GameView(context: GameActivity, screenSize: Vector2): SurfaceView(context), Runnable {

    companion object {
        private var context: Context? = null
        private var soundPool: SoundPool? = null
        private val gameStart = currentTimeMillis();
        fun getAppContext(): Context? {
            return context;
        }

        fun getGameStart(): Long {
            return gameStart;
        }

        /** Adds sound to SoundPool and Returns sound id **/
        fun addSound(resourceID: Int, priority: Int = 1): Int {
            return soundPool!!.load(
                context, resourceID, priority.toInt())
        }

        fun playSound(soundID: Int, leftVolume: Float, rightVolume: Float, priority: Int, loop: Int, rate: Float) {
            soundPool?.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    // No need to define target FPS
    // Android automatically locks the frame rate and max refresh rate

    private lateinit var thread: Thread;
    private var isPlaying = false;
    private var gameData: GameData;
    private var entities: EntityMap; // References gameData's entities
    private var music = MediaPlayer.create(context,
        R.raw.beauty_of_annihilation
    );
    private var screenSize = screenSize;
    private var paint = Paint();
    private val pixelFont = ResourcesCompat.getFont(context,
        R.font.minecraftia
    );

    init {
        // Init static members
        Companion.context = context;
        initSoundPool();
        gameData = GameData(context);
        entities = gameData.entities;

        gameData.background = Background(
            resources,
            R.drawable.background,
            screenSize.x.toInt(),
            screenSize.y.toInt()
        );
        entities.put(
            Player(
                resources,
                Vector2(screenSize.x / 2.0, screenSize.y / 2.0)
            )
        );
        paint.setTypeface(pixelFont);
        paint.textSize = 50f;
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
            gameData.removeQueuedEntities();
            draw();
        }
    }

    /** Update all entities **/
    fun update() {
        if(gameData.shouldSpawnEnemy()) gameData.spawnEnemy(resources, screenSize);
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
        drawStrings(canvas)

        holder.unlockCanvasAndPost(canvas);

    }

    fun drawStrings(canvas: Canvas) {
        paint.color = 0xcccc0000.toInt();
        var string = "Round: ${gameData.currentRound}";
        var textBounds = Rect();
        paint.getTextBounds(string, 0, string.length, textBounds);
        canvas.drawText(string,
            (screenSize.x - textBounds.width() - 50f).toFloat(),
            (screenSize.y - 25f).toFloat(), paint)

        paint.color = 0xcccccccc.toInt();
        string = "Points: ${gameData.points}"
        paint.getTextBounds(string, 0, string.length, textBounds);
        canvas.drawText(string,
            50f,
            (screenSize.y - 25f).toFloat(), paint)
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
        music.setVolume(0.8f, 0.8f);
        music.start();
    }

}

