package sample.Events;

import sample.Core.GEvent;
import sample.Core.GUnit;
import sample.Core.GameModel;

/**
 * Created by olko1016 on 01/26/2017.
 */
public class HealEvent extends GEvent {
   private final GUnit unit;
   private final int healingHp;
   private int recoveredHp;

   public HealEvent(GUnit unit, int healingHp) {
      this.unit = unit;
      this.healingHp = healingHp;
   }

   @Override
   protected void logAfterEvent() {
      GameModel.MODEL.log("base", "Recover", this, recoveredHp);
   }

   @Override
   protected void perform() {
      recoveredHp = unit.recover(healingHp);
   }
}
