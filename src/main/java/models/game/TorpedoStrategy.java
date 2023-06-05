package models.game;

import models.game.ocean.Ocean;
import models.game.ocean.Point;
import models.ships.Ship;

import java.util.EnumSet;

public class TorpedoStrategy implements GameStrategy{

    public static boolean isActive = false;

    public boolean checkMode(EnumSet<GameMode> gameMode, int torpedoCounter) {

        if (!gameMode.contains(GameMode.TORPEDO_MODE_ENABLE) && torpedoCounter > 0) {
            throw new IllegalArgumentException("ERROR: Game mode isn't TORPEDO_MODE_ENABLE, but torpedoCounter = " + torpedoCounter);
        }

        if (gameMode.contains(GameMode.TORPEDO_MODE_ENABLE)) {
            if (torpedoCounter <= 0) {
                throw new IllegalArgumentException("ERROR: Game mode is TORPEDO_MODE_ENABLE, but torpedoCounter = " + torpedoCounter);
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public AttackReport hitOnPlace(Point point, FiringMode firingMode, Ocean ocean, int availableTorpedo, int fleetHealth, EnumSet<GameMode> gameMode, Game game ) {
        if (firingMode == FiringMode.TORPEDO_FIRING_MODE && availableTorpedo <= 0) {
            throw new IllegalArgumentException("No torpedoes available");
        }
        if (firingMode == FiringMode.TORPEDO_FIRING_MODE) --availableTorpedo;

        AttackReport report;
        if (!ocean.isEmpty(point)) {
            Ship attackingShip = ocean.getShipByPosition(point);
            HealthReport healthReport = attackingShip.hitTheShip(firingMode);

            // Recalculating fleet health and torpedo available.
            fleetHealth -= healthReport.scoreByHit();
            AttackReport.HitResult result = AttackReport.getResult(healthReport.healthRemaining());

            // update if was sunk
            if (result == AttackReport.HitResult.SUNK) {
                var pointsOccupiedByShip = ocean.getPointsOccupiedByShip(attackingShip);
                report = new AttackReport(attackingShip, result, point, fleetHealth, pointsOccupiedByShip);
            } else {
                report = new AttackReport(attackingShip, result, point, fleetHealth, null);
            }
        } else {
            report = new AttackReport(null, AttackReport.getMissedResult(), point, fleetHealth, null);
        }
        return report;
    }
}
