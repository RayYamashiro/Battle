package models.game;

import models.game.ocean.Ocean;
import models.game.ocean.Point;
import models.ships.Ship;

import java.util.EnumSet;
import java.util.List;

public class RecoveryStrategy implements GameStrategy{

    private List<Point> pointsToRecover;
    private int fleetHealth;

    public RecoveryStrategy(int fleetHealth) {
        this.fleetHealth = fleetHealth;
    }

    @Override
    public AttackReport hitOnPlace(Point point, FiringMode firingMode, Ocean ocean, int availableTorpedo, int fleetHealth, EnumSet<GameMode> gameMode, Game game) {

        AttackReport report;
        if (!ocean.isEmpty(point)) {
            Ship attackingShip = ocean.getShipByPosition(point);
            HealthReport healthReport = attackingShip.hitTheShip(firingMode);

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
        if (gameMode.contains(GameMode.SHIP_RECOVERY_MODE_ENABLE)) {
            // notify that status has been changed
            game.changeStatus(report);
        }
        return report;
    }



    /**
     * Restore fleet health. (only for ship recovery mode).
     *
     * @param healthScoresToAdd health scores to add
     */
    public void restorePreviousHealthFleet(int healthScoresToAdd) {
        fleetHealth += healthScoresToAdd;
    }

    /**
     * Update list of points to recover (only for ship recovery mode).
     *
     * @param points list of points
     */
    public void updatePointsToRecover(List<Point> points) {
        pointsToRecover = points;
    }

    /**
     * (only for ship recovery mode)
     *
     * @return list of points to recover
     */
    public List<Point> getPointsToRecover() {
        return pointsToRecover;
    }

    /**
     * (only for ship recovery mode)
     * Clear list.
     */
    public void clearPointsToRecover() {
        pointsToRecover.clear();
    }
}
