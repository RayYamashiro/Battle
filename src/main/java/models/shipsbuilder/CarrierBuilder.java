package models.shipsbuilder;

import models.ships.Carrier;

public class CarrierBuilder extends ShipBuilder{
    private int health;

    public CarrierBuilder() {
        this.health = Carrier.MAX_HEALTH;
    }

    public CarrierBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

    public Carrier build() {
        Carrier carrier = new Carrier();
        carrier.setHealth(this.health);
        return carrier;
    }
}
