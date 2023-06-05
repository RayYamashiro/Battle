package models.ships;

import models.game.FiringMode;
import models.game.HealthReport;
import models.shipsbuilder.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    protected int health;

    protected Ship(int health) {
        this.health = health;
    }

    /**
     * Hit the ship with firing mode.
     *
     * @param firingMode if TORPEDO_FIRING_MODE -> ship immediately get sunk. if GENERAL_FIRING_MODE -> health--.
     * @return healthReport with health remaining and power(health) of attack.
     */
    public HealthReport hitTheShip(FiringMode firingMode) {
        if (firingMode == FiringMode.TORPEDO_FIRING_MODE) {
            int healthRemaining = health;
            health = 0;
            return new HealthReport(health, healthRemaining);
        }
        if (health > 0)
            return new HealthReport(--health, 1);
        else
            return new HealthReport(0, 0);
    }

    /**
     * @return length of the ship (MAX_HEALTH = length).
     */
    public abstract int getLength();

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to MAX_HEALTH (depends on the ship type).
     */
    public abstract void restoreHealthInRecoveryMode();

    /**
     * Converts input integers, where these numbers are listed in the order
     * which corresponds to ship types size, to list of ships.
     *
     * @param counters Array of integers.
     * @return List of the ships (sorted by length descending)
     */






}
