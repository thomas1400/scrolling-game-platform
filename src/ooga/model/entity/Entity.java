package ooga.model.entity;

import javafx.scene.image.ImageView;
import ooga.model.behavior.Collidible;
import ooga.model.behavior.CollisionBehaviorBundle;
import ooga.model.physics.Physics;
import ooga.utility.event.CollisionEvent;

public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private CollisionBehaviorBundle cbb;
  private Physics myPhysics;

  @Override
  public String[] getTags() {
    return new String[0];
  }

  @Override
  public void handleCollision(CollisionEvent ce) {

  }

  @Override
  public void updateVisualization() {

  }

  public Renderable getRenderable() {
    return null;
  }

  public Manageable getManageable() {
    return null;
  }
}
