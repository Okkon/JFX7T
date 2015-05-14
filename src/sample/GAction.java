package sample;


public interface GAction {

    boolean canSelect(Selectable obj);

    boolean canBeSelected();

    public String getName();

    GObject getOwner();

    void setOwner(GObject owner);

    void act(Selectable aim);

    void onSelect();

    void perform(Selectable obj);
}
