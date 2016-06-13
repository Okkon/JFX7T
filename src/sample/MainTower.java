package sample;


public class MainTower extends Tower {

    @Override
    public boolean canAct() {
        return baseAction.canBeSelected();
    }

}
