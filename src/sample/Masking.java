package sample;


public class Masking extends AbstractGMod {
    @Override
    public void applyEffect(GObject gObject) {
//        gObject.getVisualizer().applyEffect("hidden");
    }

    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return true;
    }
}
