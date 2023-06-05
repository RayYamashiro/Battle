package models.ships;

public class Battleship extends Ship {
    public static int MAX_HEALTH = 5;

    public Battleship() {
        super(MAX_HEALTH);
    }

    /**
     * @return max length
     */
    @Override
    public int getLength() {
        return MAX_HEALTH;
    }

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to 5.
     */
    @Override
    public void restoreHealthInRecoveryMode() {
        health = MAX_HEALTH;
    }

    /**
     * @return ship type name
     */
    @Override
    public String toString() {
        return "Battleship";
    }

    public static void setHealth(int maxHealth) {
        MAX_HEALTH = maxHealth;
    }
}
