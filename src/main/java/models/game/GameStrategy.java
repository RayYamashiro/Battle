package models.game;

import models.game.ocean.Ocean;
import models.game.ocean.Point;

import java.util.EnumSet;

public interface GameStrategy {
    public AttackReport hitOnPlace(Point point, FiringMode firingMode, Ocean ocean, int availableTorpedo, int fleetHealth, EnumSet<GameMode> gameMode, Game game);
}
