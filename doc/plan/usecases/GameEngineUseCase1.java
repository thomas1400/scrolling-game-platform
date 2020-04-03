import ooga.engine.manager.EntityManager;

public class GameEngineUseCase1 {

  /**
   * This would be code contained and called within the CollisionManager This use case details how
   * the EntityManager would check to see if a collision results in the creation of a new entity or
   * if an entity has died
   */
  public static void main(String[] args) {
    Entity entityReceived;
    EntityList myEntityList;

    boolean hasEntity = false;
    For(Entity entity : EntityList){
      if (entity.isDead()) {
        myEntityList.removeEntity(entity);
      }
      if (entity == entityReceived) {
        hasEntity = true;
      }
    }
    if (!hasEntity) {
      myEntityList.addEntity(entityReceived);
    }
  }
}