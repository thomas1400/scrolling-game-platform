package ooga.engine.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;

/**
 * This class is used to detect collisions between all the entities currently on the screen
 * This class assumes that a collision is determined by which sides of the entity are closet together
 * If the two entities overlap in their y and x coordinates, the entity is determined by if the distance between the x values or y values are smaller
 * @author Cayla Schuval
 */
public class CollisionManager {
  private ResourceBundle myCollisionLocationResources;
  private static final String MINX_MAXX = "minXmaxX";
  private static final String MINY_MAXY = "minYmaxY";
  private static final String MAXX_MINX = "maxXminX";
  private static final String MAXY_MINY = "maxYminY";
  private static final String SIDE_COLLISION = "Side";
  private static final String BOTTOM_COLLISION = "Bottom";
  private String min;
  private static Map<Double, String> map = new HashMap<>();
  private String[] collisionLocation;
  private EntityList entitiesReceived;
  private Map<Entity, EntityList> collision;
  private static final String COLLISION_DATA_FOLDER = "collisionmanagement/CollisionLocation";
  private Entity myMainEntity;


  public CollisionManager(Entity mainEntity){
    myMainEntity = mainEntity;
    myCollisionLocationResources = ResourceBundle.getBundle(COLLISION_DATA_FOLDER);
  }

  /**
   * @param entities EntityList consisting of the entities that are currently present on the screen that can collide
   * assumes that two objects only has one type of collision at a time
   */
  public void manageCollisions(EntityList entities) {
    initializeCollisionTracking(entities);
    for (Entity entity : entities) {
      for (Entity entity2 : entities) {
        if (isaValidAndNewCollision(entity, entity2)) {
          trackCollision(entity, entity2);
          double d = calculateDistances(entity, entity2);
          min = map.get(d);
          collisionLocation = myCollisionLocationResources.getString(min).split(",");
          adjustImpactFromGravityAndMultipleDetections(entity, d);
          createAndSendCollision(collisionLocation[0], entity2.getAttack(collisionLocation[1]), entity, entity2);
          createAndSendCollision(collisionLocation[1], entity.getAttack(collisionLocation[0]), entity2, entity);
        }
      }
    }
  }

  private void initializeCollisionTracking(EntityList entities){
    entitiesReceived = new EntityList();
    collision = new HashMap<>();
    map = new HashMap<>();
    for (Entity entity: entities){
      collision.put(entity, new EntityList());
    }
  }

  private boolean isaValidAndNewCollision(Entity entity, Entity entity2) {
    return !entity.equals(entity2) && !collision.get(entity).contains(entity2) && !collision.get(entity2).contains(entity) && entity.getBoundsInLocal().intersects(entity2.getBoundsInLocal());
  }

  private void trackCollision(Entity entity, Entity entity2) {
    collision.get(entity).addEntity(entity2);
    collision.get(entity2).addEntity(entity);
  }

  private double calculateDistances(Entity entity, Entity entity2) {
    map.clear();
    double minX1maxX2 = entity.getBoundsInLocal().getMinX() - entity2.getBoundsInLocal().getMaxX();
    map.put(minX1maxX2, MINX_MAXX);
    double maxX1minX2 = entity.getBoundsInLocal().getMaxX() - entity2.getBoundsInLocal().getMinX();
    map.put(maxX1minX2, MAXX_MINX);
    double minY1maxY2 = entity.getBoundsInLocal().getMinY() - entity2.getBoundsInLocal().getMaxY();
    map.put(minY1maxY2, MINY_MAXY);
    double maxY1minY2 = entity.getBoundsInLocal().getMaxY() - entity2.getBoundsInLocal().getMinY();
    map.put(maxY1minY2, MAXY_MINY);
    double min = Math.abs(minY1maxY2);
    for (double d : map.keySet()) {
      if (Math.abs(d) <= Math.abs(min)) {
          min = d;
      }
    }
    return min;
  }

  private void adjustImpactFromGravityAndMultipleDetections(Entity entity, double d) {
    if (collisionLocation[0].equals(BOTTOM_COLLISION) && entity.equals(myMainEntity)){
      entity.setY(entity.getY() - d);
    }
  }

  private void createAndSendCollision(String typeOfCollision, String attack, Entity entityToHandle, Entity other) {
    entitiesReceived.addEntity(entityToHandle.handleCollision(new CollisionEvent(typeOfCollision,
        attack, other)));
  }

  /**
   * Getter method to receive the entities that were received by the collisions
   * @return entitiesReceived will be used by the EntityManager to determine if a new entity has been created or if any of the entities died
   */
  public EntityList getEntitiesReceived() {
    return entitiesReceived;
  }

  /**
   * Getter method to receive the String signify what type of collision was identified
   * used in testing class
   * @return String associated with the type of collision
   */
  public String getMin(){
    return min;
  }

  /**
   * Getter method to receive the where the collisions occurred relative to each entity
   * used in testing class
   * @return String[] associated with where the collisions occur relative to each entity
   */
  public String[] getCollisionLocation(){
    return collisionLocation;
  }
}
