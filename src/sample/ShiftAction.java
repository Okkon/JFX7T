package sample;

public class ShiftAction extends AbstractGAction {
    public ShiftAction() {
    }

    @Override
    public void act(Selectable aim) {
        if (aim instanceof GameCell) {
            GameCell cell = (GameCell) aim;
            if (owner == null) {
                setOwner(cell.getObj());
            } else {
                owner.shift(cell);
            }
        }
    }

}
