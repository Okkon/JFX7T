package sample.Filters;

import sample.Core.GameCell;
import sample.Core.PlaceHaving;


public class VacantCellFilter extends AbstractGFilter {
    private static final VacantCellFilter INSTANCE = new VacantCellFilter();

    private VacantCellFilter() {
    }
    public static VacantCellFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        if (obj instanceof GameCell) {
            GameCell cell = (GameCell) obj;
            return cell.getObj() == null;
        }
        return false;
    }
}
