package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;
import sample.Core.Player;

public class BelongsToActivePlayerFilter extends AbstractGFilter {
    private Player player;

    @Override
    public boolean isOk(PlaceHaving obj) {
        return model.getActivePlayer().equals(((GObject) obj).getPlayer());
    }
}
