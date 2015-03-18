package sample;

/**
 * Created by kondrashov on 16.03.2015.
 */
public interface GAction {
    GAction DefaultAction = new SelectAction();

    boolean canSelect(Selectable obj);

    boolean canBeSelected();

    public String getName();

    GObject getOwner();

    void setOwner(GObject owner);

    void act(Selectable aim);

    void onSelect();
}
