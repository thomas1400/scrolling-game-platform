package ooga.model.entity;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.ImageView;
import ooga.model.ability.Ability;
import ooga.model.ability.Health;
import ooga.model.behavior.Collidible;
import ooga.model.behavior.CollisionBehaviorBundle;
import ooga.model.physics.Physics;
import ooga.utility.event.CollisionEvent;

public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private CollisionBehaviorBundle cbb;
  private Physics myPhysics;
  private List<Ability> myAbilities;

  public Entity(){
    myAbilities = new ArrayList<Ability>();
  }

  public void addAbility(Ability a){
    //TODO add a check here for if an ability of that type is already here. depending on how we do attacks then we might want to only allow one of each 'type'
    myAbilities.add(a);
  }

  @Override
  public String[] getTags() {
    return new String[0];
  }

  @Override
  public void handleCollision(CollisionEvent ce) {
    for (Ability a : myAbilities){
      a.hit();
    }
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
