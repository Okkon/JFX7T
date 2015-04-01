package sample;


public class AbstractGMod implements GMod {
    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public boolean blocksTower() {
        return false;
    }

    @Override
    public void onHit(Hit hit) {

    }

    @Override
    public void onTakeHit(Hit hit) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
