package models.shipsbuilder;

import models.ships.Carrier;
import models.ships.Destroyer;

public class DestroyerBuilder extends ShipBuilder{
    private int health;

    public DestroyerBuilder() {
        this.health = Destroyer.MAX_HEALTH;
    }

    public DestroyerBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

    public Destroyer build() {
        Destroyer destroyer = new Destroyer();
        destroyer.setHealth(this.health);
        return destroyer;
    }
}
