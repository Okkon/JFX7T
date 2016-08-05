package sample.Filters;

import sample.GameCell;
import sample.Selectable;

/**
 * Created by kondrashov on 05.08.2016.
 */
public class IsVacantCellFilter extends AbstractGFilter {
    @Override
    public boolean isOk(Selectable obj) {
        if (obj instanceof GameCell) {
            GameCell cell = (GameCell) obj;
            return cell.getObj() == null;
        }
        return false;
    }
}
