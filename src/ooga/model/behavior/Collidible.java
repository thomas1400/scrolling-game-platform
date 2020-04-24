package ooga.model.behavior;

import java.util.Map;
import ooga.model.ability.Ability;
import ooga.model.entity.Entity;
import ooga.utility.event.CollisionEvent;

public interface Collidible {

  Map<String, Ability> getAbilities();

  boolean isDead();

  double getData(String informationType);

  void otherCollectMe();

  void size(Double value);

  String getAttack(String location);

  //double getScore();
  void otherResetAfterCollect();

  //double getScale();

}
