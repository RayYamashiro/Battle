package models.ships;

public class Cruiser extends Ship {
    public static int MAX_HEALTH = 3;

    public Cruiser() {
        super(MAX_HEALTH);
    }

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to 3.
     */
    @Override
    public void restoreHealthInRecoveryMode() {
        health = MAX_HEALTH;
    }

    public static void setHealth(int maxHealth) {
        MAX_HEALTH = maxHealth;
    }
    /**
     * @return max length
     */
    @Override
    public int getLength() {
        return MAX_HEALTH;
    }

    /**
     * @return ship type name
     */
    @Override
    public String toString() {
        return "Cruiser";
    }

}
