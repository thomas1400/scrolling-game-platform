package plan.model.entity;

import javafx.scene.image.ImageView;
import plan.model.behavior.Collidible;
import plan.model.behavior.CollisionBehaviorBundle;
import plan.utility.event.CollisionEvent;
import plan.model.physics.Physics;

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
