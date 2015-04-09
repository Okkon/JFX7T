package sample;


public class Masking extends AbstractGMod {
    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return true;
    }
}
