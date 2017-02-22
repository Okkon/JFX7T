package sample.GlobalMods;

import sample.Core.GObject;
import sample.Core.GlobalMod;
import sample.Core.PlaceHaving;
import sample.Events.FilterSelectionEvent;
import sample.Filters.AbstractGFilter;
import sample.Skills.ShotAction;

public class MaskingMod<T extends FilterSelectionEvent, H extends GObject> extends GlobalMod<T, H> {
    private static final MaskingMod INSTANCE = new MaskingMod();
    private final AbstractGFilter maskingFilter = new AbstractGFilter() {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return !getHolders().contains(obj);
        }
    };

    private MaskingMod() {
        eventClass = FilterSelectionEvent.class;
    }

    public static MaskingMod getInstance() {
        return INSTANCE;
    }

    @Override
    public void doBeforeEvent(T event) {
        if (event.getAction() instanceof ShotAction) {
            event.getAimFilters().add(maskingFilter);
        }
    }
}
