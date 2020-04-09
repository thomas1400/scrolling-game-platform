package ooga.controller;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public interface Communicable {

  void addEntity(Entity entity);

  void removeEntity(Entity entity);

  void addAllEntities(EntityList entities);

  void removeAllEntities(EntityList entities);


}
