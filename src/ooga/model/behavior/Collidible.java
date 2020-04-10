package ooga.model.behavior;

import ooga.model.entity.Entity;
import ooga.utility.event.CollisionEvent;

public interface Collidible {

  String[] getTags();

  Entity handleCollision(CollisionEvent ce);

}
