package sample.Events;

import sample.Core.*;

/**
 * Created by olko1016 on 01/26/2017.
 */
public class PushCollisionEvent extends GEvent {
   private final GUnit pushedUnit;
   private final GObject objPushedTo;

   public PushCollisionEvent(GUnit pushedUnit, GObject objPushedTo) {
      this.pushedUnit = pushedUnit;
      this.objPushedTo = objPushedTo;
   }

   @Override
   protected void perform() {
      new HitEvent(Hit.createHit(pushedUnit, objPushedTo, 1, 0, DamageType.PHYSICAL)).process();
      new HitEvent(Hit.createHit(objPushedTo, pushedUnit, 1, 0, DamageType.PHYSICAL)).process();
   }
}
