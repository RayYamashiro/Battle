package models.shipsbuilder;


import models.ships.Submarine;

public class SubmarineBuilder extends ShipBuilder{
    private int health;

    public SubmarineBuilder() {
        this.health = Submarine.MAX_HEALTH;
    }

    public SubmarineBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

    public Submarine build() {
        Submarine submarine = new Submarine();
        submarine.setHealth(this.health);
        return submarine;
    }
}
