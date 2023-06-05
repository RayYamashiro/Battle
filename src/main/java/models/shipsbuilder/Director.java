package models.shipsbuilder;

import models.ships.Ship;

public class Director {
    public static Ship manage(ShipBuilder builder)
    {
        return new ShipBuilder().build(builder);
    }
}
