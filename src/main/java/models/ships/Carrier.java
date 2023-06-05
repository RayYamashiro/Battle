package models.ships;

public class Carrier extends Ship {
    //public static int MAX_HEALTH = 5;

    public static int MAX_HEALTH = 4;

    public Carrier() {
        super(MAX_HEALTH);
    }

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to 4.
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

    /**
     * @return ship type name
     */
    @Override
    public String toString() {
        return "Carrier";
    }

    public static void setHealth(int maxHealth) {
        MAX_HEALTH = maxHealth;
    }
}
