package models.game;

import models.game.ocean.Ocean;
import models.game.ocean.Point;
import models.game.user.StatusChangedListener;
import models.ships.Ship;
import models.shipsbuilder.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Game {
    private Ocean ocean;
    private int fleetHealth;
    private int availableTorpedo;
    private EnumSet<GameMode> gameMode;

    public TorpedoStrategy torpedoStrategy = new TorpedoStrategy();
    public RecoveryStrategy recoveryStrategy = new RecoveryStrategy(fleetHealth);

    /**
     * Create new game.
     *
     * @param sizeHorizontal horizontal size of the ocean
     * @param sizeVertical   vertical size of the ocean
     * @throws IllegalArgumentException cannot create ocean with this parameters
     */
    public Game(int sizeHorizontal, int sizeVertical) throws IllegalArgumentException {
        ocean = new Ocean(sizeHorizontal, sizeVertical);
    }

    /**
     * Set the game mode.
     *
     * @param mode           enum set of options
     * @param torpedoCounter torpedo counter
     * @throws IllegalArgumentException when torpedo counter mismatching with game mode
     */
    public void setGameMode(EnumSet<GameMode> mode, int torpedoCounter) throws IllegalArgumentException {
        // if mode == null: game without torpedo mode and recovery mode.
        gameMode = mode;

        if( torpedoStrategy.checkMode(gameMode , torpedoCounter)) {
            availableTorpedo = torpedoCounter;
        } else {
            availableTorpedo = 0;
        }
    }

    /**
     * @param torpedoCounter number of available torpedo
     * @param recoveryMode   recovery mode turned on/off
     * @return Game mode
     */
    public static EnumSet<GameMode> recognizeMode(int torpedoCounter, boolean recoveryMode) {
        if (torpedoCounter > 0 && recoveryMode) return GameMode.All_OPTIONS;
        if (torpedoCounter > 0) return EnumSet.of(GameMode.TORPEDO_MODE_ENABLE);
        if (recoveryMode) return EnumSet.of(GameMode.SHIP_RECOVERY_MODE_ENABLE);
        else return EnumSet.of(GameMode.NO_OPTIONS);
    }

    /**
     * Placing ship on the Ocean.
     *
     * @param counters array of numbers, where these numbers are listed in the order
     *                 which corresponds to ship types sizes
     * @throws RuntimeException if ocean size is too small for the input specified fleet
     */
    public void placeShipsOnOcean(int[] counters) throws RuntimeException {
        List<Ship> ships = convertInputIntegersToShips(counters);
        fleetHealth = ships.stream().mapToInt(Ship::getLength).sum();

        // Fleet size bigger than ocean size
        if (ocean.getSizeVertical() * ocean.getSizeHorizontal() <= fleetHealth)
            throw new RuntimeException("Cannot place ships on this ocean");
        this.ocean = Ocean.randomPlace(ships, ocean);

        if (ocean == null)
            throw new RuntimeException("Cannot place ships on this ocean");
    }

    public static List<Ship> convertInputIntegersToShips(int[] counters) {
        List<Ship> ships = new ArrayList<>();

        for (int i = 0; i < counters.length; i++) {
            for (int j = 0; j < counters[i]; j++) {
                ShipBuilder builder;
                switch (i) {
                    case 0:
                        builder = new CarrierBuilder();
                        ships.add(Director.manage(builder));
                        break;
                    case 1:
                        builder = new BattleshipBuilder();
                        ships.add(Director.manage(builder));
                        break;
                    case 2:
                        builder = new CruiserBuilder();
                        ships.add(Director.manage(builder));
                        break;
                    case 3:
                        builder = new DestroyerBuilder();
                        ships.add(Director.manage(builder));
                        break;
                    case 4:
                        builder = new SubmarineBuilder();
                        ships.add(Director.manage(builder));
                        break;
                }
            }
        }
        return ships;
    }
    private final List<StatusChangedListener> listeners = new ArrayList<>();

    /**
     * (Only for recovery mode) Subscribes listener to user status changed event.
     *
     * @param user user to subscribe.
     */
    public void addListener(StatusChangedListener user) {
        listeners.add(user);
    }

    /**
     * Notify everybody in listeners list.
     *
     * @param report report of current attack
     */
    protected void changeStatus(AttackReport report) {
        for (StatusChangedListener user : listeners)
            user.onStatusChanged(report);
    }

    /**
     * Attack on specified point on ocean.
     *
     * @param point      point to attack
     * @param firingMode general / torpedo
     * @return attack report
     * @throws IllegalArgumentException if firing mode = torpedo and there is no available torpedo
     */
    public AttackReport hitOnPlace(Point point, FiringMode firingMode) throws IllegalArgumentException {
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

        if (gameMode.contains(GameMode.SHIP_RECOVERY_MODE_ENABLE)) {
            // notify that status has been changed
            changeStatus(report);
        }
        return report;
    }


}
