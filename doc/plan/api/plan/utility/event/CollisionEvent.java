package plan.utility.event;

import plan.model.entity.Entity;

public class CollisionEvent extends Event {

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
