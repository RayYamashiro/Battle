package models.shipsbuilder;

import models.ships.Battleship;
import models.ships.Carrier;

public class BattleshipBuilder extends ShipBuilder{
    private int health;

    public BattleshipBuilder() {
        this.health = Battleship.MAX_HEALTH;
    }

    public BattleshipBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

    public Battleship build() {
        Battleship battleship = new Battleship();
        battleship.setHealth(this.health);
        return battleship;
    }
}
