package models.shipsbuilder;

import models.ships.Carrier;
import models.ships.Cruiser;

public class CruiserBuilder extends ShipBuilder{
    private int health;

    public CruiserBuilder() {
        this.health = Cruiser.MAX_HEALTH;
    }

    public CruiserBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

    public Cruiser build() {
        Cruiser cruiser = new Cruiser();
        cruiser.setHealth(this.health);
        return cruiser;
    }
}
