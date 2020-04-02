package plan.model.behavior;

import plan.utility.event.CollisionEvent;

public interface Collidible {

  String[] getTags();

  void handleCollision(CollisionEvent ce);

}
