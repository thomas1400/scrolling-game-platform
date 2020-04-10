package ooga.model.behavior;

import ooga.utility.event.CollisionEvent;

public interface Collidible {

  String[] getTags();

  void handleCollision(CollisionEvent ce);

}
