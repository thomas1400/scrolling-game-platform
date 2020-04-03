import ooga.engine.manager.CollisionManager;

public class GameEngineUseCase2 {

  /**
   * This would be code contained and called within the CollisionManager
   * This use case details how the Collision manager would detect a collision between entities and then create two Collision objects that are passed to the respective entities
   * The CollisionManager then has to tell the EntityManager that a Collision has occurred and an entity has been received
   */
  public static void main(String[] args) {
    Collision collision1;
    Collision collision2;
    EntityManager myEntityManager;

    For(Entity entity: EntityList){
      For(Entity entity2 ); EntityList){
        if(!entity.equals(entity2)){
          if(entity.getBoundsInParent.intersections(entity2.getBoundsInParent)){
            if(entity.getX()>entity2.getX()) {
              collision1 = new Collision(entity2, "left");
              collision2 = new Collision(entity, "right");
            }
              else if(entity.getX()<entity2.getX()){
                collision1 = new Collision(entity2, "right");
                collision2 = new Collision(entity, "left");
              }
              else if(entity.getY()<entity2.getY()){
                collision1 = new Collision(entity2, "above");
                collision2 = new Collision(entity, "below");
              }
              else if(entity.getY()>entity2.getY()){
                collision1 = new Collision(entity2, "below");
                collision2 = new Collision(entity, "above");
              }
              Entity returnEntity = entity.pass(collision1);
              Entity returnEntity2 = entity.pass(collision2);
              myEntityManager.collisionOccured(returnEntity);
              myEntityManager.collisionOccured(returnEntity2);
            }
          }
        }
      }
  }
}

