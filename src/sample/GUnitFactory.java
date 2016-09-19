package sample;


import sample.Core.GUnit;
import sample.GActions.RoundAttackAction;
import sample.GActions.SwingAttackAction;
import sample.GlobalMods.*;
import sample.Mods.*;
import sample.Skills.AstralBow;
import sample.Skills.Crossbow;
import sample.Skills.Fireball;

import java.util.Arrays;
import java.util.List;

public class GUnitFactory {
    public enum UnitClass {
        Archer, Footman, Assassin, Mage, Inquisitor,

        Troll(UnitType.additional),
        AstralArcher(UnitType.additional);

        public UnitType type;

        public enum UnitType {
            base, additional
        }

        UnitClass(UnitType type) {
            this.type = type;
        }

        UnitClass() {
            this(UnitType.base);
        }
    }

    public List<String> unitTypesList = Arrays.asList("Archer", "Footman", "Assassin", "Mage", "Inquisitor", "Troll", "AstralArcher");

    public static GUnit create(UnitClass type) {
        return create(type.name());
    }

    public static GUnit create(String type) {
        GUnit unit = null;
        switch (type) {
            case "Footman": {
                unit = new GUnit(4, 21, 2, 2);
                unit.setAttackAction(SwingAttackAction.getInstance());
                unit.addMod(new Armor(2));
                unit.addMod(new Shield(2));
                break;
            }
            case "Inquisitor": {
                unit = new GUnit(5, 26, 2, 2);
                unit.addMod(MagicSword.getInstance());
                unit.addMod(new MagicArmor(2));
                break;
            }
            case "Archer": {
                unit = new GUnit(2, 21, 1, 1);
                unit.addSkill(new Crossbow(2, 2, 60));
                break;
            }
            case "Mage": {
                unit = new GUnit(1, 25, 0, 2);
                unit.addSkill(new Fireball(1, 2, 60));
                break;
            }
            case "Assassin": {
                unit = new GUnit(4, 36, 1, 2);
                unit.setAttackAction(RoundAttackAction.getInstance());
                unit.addMod(Masking.getInstance());
                unit.addMod(Sabotage.getInstance());
                unit.addGlobalMod(PushAttackMod.getInstance());
                break;
            }
            case "AstralArcher": {
                unit = new GUnit(3, 26, 1, 2);
                unit.addMod(new MagicArmor(2));
                unit.addSkill(new AstralBow(1, 2, 60));
                break;
            }
            case "Troll": {
                unit = new GUnit(6, 21, 2, 3);
                unit.addMod(new Regeneration(3));
                break;
            }
        }

        unit.setType(type);
        return unit;
    }
}
