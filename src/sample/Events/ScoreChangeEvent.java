package sample.Events;

import sample.Core.GEvent;
import sample.Core.GameCell;
import sample.Core.Player;


public class ScoreChangeEvent extends GEvent {
    private Player owner;
    private final int points;
    private final GameCell place;

    public ScoreChangeEvent(Player owner, int points, GameCell place) {
        this.owner = owner;
        this.points = points;
        this.place = place;
    }

    @Override
    protected void perform() {
        owner.score(points);
        model.getGraphics().showScoring(this);
    }
}
