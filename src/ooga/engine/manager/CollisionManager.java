package ooga.engine.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;
import ooga.utility.observer.Observer;


public class CollisionManager {
  private ResourceBundle myCollisionLocationResources;
  private static final String CollisionLocationResources = "entities/collisions/CollisionLocation";
  private static final String SIDE = "Side";
  private static final String TOP = "Top";
  private static final String BOTTOM = "Bottom";
  private double minX1maxX2 = 0;
  private double maxX1minX2 = 0;
  private double minY1maxY2 = 0;
  private double maxY1minY2 = 0;
  private static final String MINX_MAXX = "minXmaxX";
  private static final String MINY_MAXY = "minYmaxY";
  private static final String MAXX_MINX = "maxXminX";
  private static final String MAXY_MINY = "maxYminY";
  private static Map<Double, String> map = new HashMap<>();
  private static Map<Entity, EntityList> collision = new HashMap<>();


  private List<Observer> observers;
  private EntityList entitiesReceived;

  public CollisionManager(){
    myCollisionLocationResources = ResourceBundle.getBundle(CollisionLocationResources);
  }

  public void manageCollisions(EntityList entities) {
    map = new HashMap<>();
    entitiesReceived = new EntityList();
    collision = new HashMap<>();
    for (Entity entity : entities) {
      collision.putIfAbsent(entity, new EntityList());
      for (Entity entity2 : entities) {
        collision.putIfAbsent(entity2, new EntityList());
        if (!entity.equals(entity2) && !collision.get(entity).contains(entity2) && !collision.get(entity2).contains(entity)) {
          if (entity.getBoundsInLocal().intersects(entity2.getBoundsInLocal())) {
            collision.get(entity).addEntity(entity2);
            collision.get(entity2).addEntity(entity);
            double d = calculateDistances(entity, entity2);
            String min = map.get(d);
            if(min.equals("maxYminY")){
              entity.setY(entity.getY() - d);
            }
            String[] results = myCollisionLocationResources.getString(min).split(",");
            createAndSendCollision(results[0], entity2.getAttack(results[1]), entity2, entity);
            createAndSendCollision(results[1], entity.getAttack(results[0]), entity, entity2);
          }
        }
      }
    }
  }

  private double calculateDistances(Entity entity, Entity entity2) {
    map.clear();
    minX1maxX2 = Math
        .abs(entity.getBoundsInLocal().getMinX() - entity2.getBoundsInLocal().getMaxX());
    map.put(minX1maxX2, MINX_MAXX);
    maxX1minX2 = Math
        .abs(entity.getBoundsInLocal().getMaxX() - entity2.getBoundsInLocal().getMinX());
    map.put(maxX1minX2, MAXX_MINX);
    minY1maxY2 = Math
        .abs(entity.getBoundsInLocal().getMinY() - entity2.getBoundsInLocal().getMaxY());
    map.put(minY1maxY2, MINY_MAXY);
    maxY1minY2 = Math
        .abs(entity.getBoundsInLocal().getMaxY() - entity2.getBoundsInLocal().getMinY());
    map.put(maxY1minY2, MAXY_MINY);
    double min = minY1maxY2;
    for (double d : map.keySet()) {
      if (d <= min) {
        //System.out.println("entity max y:" + entity.getBoundsInLocal().getMaxY());
        //System.out.println("entity 2 min y: " + entity2.getBoundsInLocal().getMinY());
        /*if((map.get(d).equals("maxXminX") || map.get(d).equals("minXmaxX")) && (entity.getBoundsInLocal().getMaxY()-.4<entity2.getBoundsInLocal().getMinY())){
          //System.out.println("hi");
          continue;
        }*/
        /*else if((map.get(d).equals("maxYminY") || map.get(d).equals("minYmaxY")) && (entity.getBoundsInLocal().getMaxX()-.4<entity2.getBoundsInLocal().getMinX())){
          continue;
        }*/

          min = d;
      }
    }
    return min;
  }




  private void createAndSendCollision(String typeOfCollision, String attack, Entity entityToHandle, Entity other) {
    //receive an entity object from the entity
    entitiesReceived.addEntity(entityToHandle.handleCollision(new CollisionEvent(typeOfCollision, attack, other)));
  }

  public EntityList getEntitiesReceived() {
    return entitiesReceived;
  }
}
