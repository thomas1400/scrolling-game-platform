package ooga.engine.manager;

import ooga.controller.Communicable;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class EntityManager implements Communicable {
  Entity entityReceived;
  EntityList myEntityList;

  private CollisionManager myCollisionManager;

  public EntityManager() { }

  protected void manageEntities(EntityList entities) {


    boolean hasEntity = false;
    for (Entity entity: entities){
      if (entity.isDead()) {
        removeEntity(entity);
      }
      if (entity == entityReceived) {
        hasEntity = true;
      }
    }
    if (!hasEntity) {
      addEntity(entityReceived);
    }
  }
}

  }

  public void addEntity() {
  }

  public void removeEntity() {

  }

}
