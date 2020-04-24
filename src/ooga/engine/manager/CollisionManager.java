package ooga.engine.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;


public class CollisionManager {
  private ResourceBundle myCollisionLocationResources;

  private static final String MINX_MAXX = "minXmaxX";
  private static final String MINY_MAXY = "minYmaxY";
  private static final String MAXX_MINX = "maxXminX";
  private static final String MAXY_MINY = "maxYminY";
  private static final String SIDE_COLLISION = "Side";
  private String min;
  private static Map<Double, String> map = new HashMap<>();
  private String[] results;
  private EntityList entitiesReceived;
  private Map<Entity, EntityList> collision;

  public CollisionManager(String gameType){
    String CollisionLocationResources = "gamedata/"+gameType+"/entities"
        + "/collisions/CollisionLocation";
    myCollisionLocationResources = ResourceBundle.getBundle(CollisionLocationResources);
  }

  public void manageCollisions(EntityList entities) {
    map = new HashMap<>();
    initializeCollisionTracking(entities);
    for (Entity entity : entities) {
      for (Entity entity2 : entities) {
        if (isaValidAndNewCollision(entity, entity2)) {
          trackCollision(entity, entity2);
          double d = calculateDistances(entity, entity2);
          min = map.get(d);
          results = myCollisionLocationResources.getString(min).split(",");
          adjustImpactFromGravityAndMultipleDetections(entity, d);
          createAndSendCollision(results[0], entity2.getAttack(results[1]), entity, entity2);
          createAndSendCollision(results[1], entity.getAttack(results[0]), entity2, entity);
        }
      }
    }
  }

  private void initializeCollisionTracking(EntityList entities){
    entitiesReceived = new EntityList();
    collision = new HashMap<>();
    collision.clear();
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
    if(results[0].equals(SIDE_COLLISION)){
      entity.setX(entity.getX() - d);
    }
    else{
      entity.setY(entity.getY() - d);
    }
  }

  private void createAndSendCollision(String typeOfCollision, String attack, Entity entityToHandle, Entity other) {
    entitiesReceived.addEntity(entityToHandle.handleCollision(new CollisionEvent(typeOfCollision,
        attack, other)));
  }

  public EntityList getEntitiesReceived() {
    return entitiesReceived;
  }

  public String getMin(){
    return min;
  }

  public String[] getResults(){
    return results;
  }
}
