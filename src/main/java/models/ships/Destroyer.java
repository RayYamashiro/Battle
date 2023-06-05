package battleship.model.ships;

public class Destroyer extends Ship {
    private static final int MAX_HEALTH = 2;

    public Destroyer() {
        super(MAX_HEALTH);
    }

    /**
     * When the recovery mode is enabled this method restore.
     * health of the ship to 2.
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
        return "Destroyer";
    }
}
