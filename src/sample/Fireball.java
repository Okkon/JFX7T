package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kondrashov on 26.03.2015.
 */
public class Fireball extends ShotAction {
    public Fireball(int minDam, int randDam, int maxDistance) {
        super(minDam, randDam, maxDistance);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void hit(GObject obj) {
                List<GObject> list = new ArrayList<GObject>();
                list.add(obj);
                list.addAll(GameModel.MODEL.getNearUnits(obj.getPlace()));
                for (GObject object : list) {
                    final Hit hit = Hit.createHit(attacker, object, minDamage, maxDamage, damageType);
                    object.takeHit(hit);
                }
                obj.push(direction);

            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Fireball");
        shell.setDamageType(DamageType.MAGIC);
    }
}
