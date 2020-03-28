package com.kyle.last_man_standing.actor

import android.content.res.Resources
import android.graphics.Canvas
import android.view.MotionEvent
import com.kyle.last_man_standing.weapon.AssaultRifle
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.weapon.Weapon
import com.kyle.last_man_standing.game.GameData
import com.kyle.last_man_standing.weapon.Minigun
import com.kyle.last_man_standing.weapon.Pistol
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

const val PLAYER_SCALE: Float = 3.2f; // Image scaling for player
const val CIRCLE_SIZE = 300;
class Player(res: Resources, location: Vector2): Actor(res,
    R.drawable.player,
    PLAYER_SCALE, 8.0, 100.0, location) { // Resources are the actual resources while R only gets the id

    var normalSpeed = speed;
    var speedPowerupStopTime = 0L;

    private var circle = PlayerCircle(
        res,
        CIRCLE_SIZE,
        location
    );
    var weapon: Weapon = Pistol(res, PLAYER_SCALE, location);

    init {
        weapon.addOffset(image.width / 5.3, image.height / 2.75);
    }

    override fun update(gameData: GameData) {
        if(gameData.isTouchInitialized() && gameData.lastTouch.action != MotionEvent.ACTION_UP){
            touchUpdate(gameData.lastTouch)
        };
        circle.update(gameData);
        weapon.update(gameData);

        if(speed != normalSpeed && currentTimeMillis() > speedPowerupStopTime) {
            speed = normalSpeed;
        }

        super.update(gameData);
    }

    fun touchUpdate(lastTouch: MotionEvent) {
        val touchLocation = Vector2(lastTouch.x.toDouble(), lastTouch.y.toDouble());
        faceLocation(touchLocation);

        val difVec = touchLocation.subCopy(location) as Vector2;
        val difVecNorm = difVec.normaliseCopy() as Vector2;

        val isTouchInsideCircle = difVec.magnitude() < CIRCLE_SIZE / 2;
        if(!isTouchInsideCircle){
            x += difVecNorm.x * speed;
            y += difVecNorm.y * speed;
        }

        circle.location = this.location;
        weapon.location = this.location;
        weapon.rotation = this.rotation;
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas);
        circle.draw(canvas);
        weapon.draw(canvas);
    }

}