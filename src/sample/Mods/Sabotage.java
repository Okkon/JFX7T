package sample.Mods;


import sample.AbstractGMod;

public class Sabotage extends AbstractGMod {
    @Override
    public boolean blocksTower() {
        return true;
    }
}
