package sample.Events;

import sample.Core.GEvent;
import sample.Core.GameCell;
import sample.Core.GameModel;
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
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "Score", this, points);
    }

    @Override
    protected void perform() {
        owner.changeScore(points);
        model.getGraphics().showScoring(this);
    }
}
