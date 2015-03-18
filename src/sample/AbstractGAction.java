package sample;

/**
 * Created by kondrashov on 18.02.2015.
 */
public abstract class AbstractGAction implements GAction {
    protected GObject owner;

    @Override
    public void onSelect() {

    }

    @Override
    public GObject getOwner() {
        return owner;
    }

    @Override
    public void setOwner(GObject owner) {
        this.owner = owner;
    }

    public void act(Selectable obj) {

    }

    @Override
    public boolean canSelect(Selectable obj) {
        return true;
    }

    @Override
    public boolean canBeSelected() {
        return true;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
