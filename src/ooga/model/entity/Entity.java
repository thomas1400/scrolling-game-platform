package ooga.model.entity;

import ooga.model.behavior.Collidible;
import ooga.model.behavior.CollisionBehaviorBundle;
import ooga.utility.event.CollisionEvent;
import ooga.model.physics.Physics;

public class Entity implements Collidible {

  private CollisionBehaviorBundle cbb;
  private Physics myPhysics;

  @Override
  public String[] getTags() {
    return new String[0];
  }

  @Override
  public void handleCollision(CollisionEvent ce) {

  }
}
