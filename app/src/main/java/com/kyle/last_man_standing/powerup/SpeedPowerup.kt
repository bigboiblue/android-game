import android.content.res.Resources
import com.kyle.last_man_standing.R
import com.kyle.last_man_standing.actor.Player
import com.kyle.last_man_standing.powerup.Powerup
import mikera.vectorz.Vector2
import java.lang.System.currentTimeMillis

class SpeedPowerup(res: Resources, scale: Float, location: Vector2): Powerup(res, R.drawable.speed_powerup, scale, location){
    val powerupTime = 15000L; // 15 seconds in millis

    override fun givePowerup(player: Player) {
        player.speed = player.speed + 5;
        player.speedPowerupStopTime = currentTimeMillis() + powerupTime;
    }
}