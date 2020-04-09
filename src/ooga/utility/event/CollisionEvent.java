package ooga.utility.event;

import ooga.model.entity.Entity;

public class CollisionEvent extends Event {

  /*
  * Note (Thomas): this is all starter code that I just added for fun during plan! Feel free
  * to remove/change any/all of this!
   */


  private Entity a, b;
  private Side aSide, bSide;

  public enum Side {
    TOP,
    BOTTOM,
    SIDE
  }

  public CollisionEvent(Entity a, Side aSide, Entity b, Side bSide) { }

  public Entity getOther(Entity one) {
    if (a == one) {
      return b;
    } else {
      return a;
    }
  }
}
