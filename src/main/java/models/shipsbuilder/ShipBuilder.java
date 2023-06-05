package models.shipsbuilder;

import models.ships.Ship;

public class ShipBuilder {
    public Ship build(ShipBuilder shipBuilder)
    {
        if (shipBuilder instanceof CarrierBuilder) {
            CarrierBuilder b = (CarrierBuilder) shipBuilder;
            return b.build();
        }
        else if (shipBuilder instanceof BattleshipBuilder) {
            BattleshipBuilder t = (BattleshipBuilder) shipBuilder;
            return t.build();
        }
        else if(shipBuilder instanceof CruiserBuilder)
        {
            CruiserBuilder c = (CruiserBuilder) shipBuilder;
            return c.build();
         }
        else if(shipBuilder instanceof DestroyerBuilder)
        {
            DestroyerBuilder d = (DestroyerBuilder) shipBuilder;
            return d.build();
        }
        else if(shipBuilder instanceof SubmarineBuilder)
        {
            SubmarineBuilder s = (SubmarineBuilder) shipBuilder;
            return s.build();
        }
        return null;
    }
}
