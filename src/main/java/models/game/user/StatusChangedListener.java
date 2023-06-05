package models.game.user;

import models.game.AttackReport;

public interface StatusChangedListener {
    /**
     * method to notify listener that status has been changed
     *
     * @param report attacking report
     */
    void onStatusChanged(AttackReport report);
}
