package sample.Filters;

import sample.Core.GameCell;
import sample.Core.PlaceHaving;

/**
 * Created by kondrashov on 05.08.2016.
 */
public class IsVacantCellFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        if (obj instanceof GameCell) {
            GameCell cell = (GameCell) obj;
            return cell.getObj() == null;
        }
        return false;
    }
}
