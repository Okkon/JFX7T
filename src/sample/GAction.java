package sample;


public interface GAction {
    GAction DefaultAction = new SelectAction();

    boolean canSelect(Selectable obj);

    boolean canBeSelected();

    public String getName();

    GObject getOwner();

    void setOwner(GObject owner);

    void act(Selectable aim);

    void onSelect();


    void perform(Selectable obj);
}
