package com.kyle.last_man_standing.powerup

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.actor.Player
import com.kyle.last_man_standing.weapon.AssaultRifle
import com.kyle.last_man_standing.weapon.Minigun
import com.kyle.last_man_standing.weapon.Pistol
import mikera.vectorz.Vector2
import java.util.*

class WeaponPowerup(res: Resources, scale: Float, location: Vector2): Powerup(res, R.drawable.random_gun, scale, location) {
    var weapon = Pistol::class.java.name;

    init {
        var num = Random().nextInt(3);
        when(num){
            0 -> weapon = Pistol::class.java.name;
            1 -> weapon = AssaultRifle::class.java.name;
            2 -> weapon = Minigun::class.java.name;
        }

        num = Random().nextInt(4)
        if(num > 0) changeImage();
    }

    fun changeImage() {
        var imageID = R.drawable.pistol;
        when(weapon) {
            Pistol::class.java.name -> {
                imageID = R.drawable.pistol_powerup
                scale = scale / 1f;
            };
            AssaultRifle::class.java.name -> {
                imageID = R.drawable.ar_powerup
                scale = scale / 1.4f;
            }
            Minigun::class.java.name -> {
                imageID = R.drawable.minigun_powerup
                scale = scale / 1.4f;
            }
        }
        val bitmapOptions = BitmapFactory.Options();
        bitmapOptions.inScaled = false; // Disable upscaling smoothing
        val bitmap = BitmapFactory.decodeResource(res, imageID, bitmapOptions);
        image = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), false)
    }

    override fun givePowerup(player: Player) {
        when(weapon) {
            Pistol::class.java.name -> player.weapon = Pistol(res, player.scale, player.location)
            AssaultRifle::class.java.name -> player.weapon = AssaultRifle(res, player.scale, player.location)
            Minigun::class.java.name -> player.weapon = Minigun(res, player.scale, player.location)
        }
        player.weapon.addOffset(player.image.width / 5.3, player.image.height / 2.75);
    }
}