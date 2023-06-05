package models.ships;

public class Submarine extends Ship {
    public static  int MAX_HEALTH = 1;

    public Submarine() {
        super(MAX_HEALTH);
    }

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to 1.
     */
    @Override
    public void restoreHealthInRecoveryMode() {
        health = MAX_HEALTH;
    }

    /**
     * @return max length
     */
    @Override
    public int getLength() {
        return MAX_HEALTH;
    }

    public static void setHealth(int maxHealth) {
        MAX_HEALTH = maxHealth;
    }

    /**
     * @return ship type name
     */

    @Override
    public String toString() {
        return "Submarine";
    }
}
