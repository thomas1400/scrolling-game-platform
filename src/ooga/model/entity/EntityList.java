package ooga.model.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ooga.exceptions.ExceptionFeedback;

/**
 * Custom data structure that is used to store and access entities
 * Implements Iterable<Entity></Entity> so that a for each loop can be used to iterate through the entities
 * @author Cayla Schuval
 */
public class EntityList implements Iterable<Entity> {
  private List<Entity> myEntities;
  private Entity myMainEntity;
  private static final String EXCEPTION_MESSAGE = "No main entity set";


  /**
   * Creates a new instance of an EntityList
   */
  public EntityList(){
    myEntities = new ArrayList<>();
  }

  public void addEntity(Entity entityReceived) {
    myEntities.add(entityReceived);
  }

  public void removeEntity(Entity entity) {
    myEntities.remove(entity);
  }

  public void addAllEntities(EntityList entities){
    myEntities.addAll(entities.getAsList());
  }

  public void removeAllEntities(EntityList entities){
    myEntities.removeAll(entities.getAsList());
  }

  public boolean contains(Entity entity) {
    return myEntities.contains(entity);
  }

  public int size(){
    return myEntities.size();
  }

  public Iterator<Entity> iterator() {
    return myEntities.iterator();
  }

  public void clear(){ myEntities.clear(); }

  public Entity getMainEntity() {
    try {
      return myMainEntity;
    }
    //fix
    catch (Exception e){
      ExceptionFeedback.throwBreakingException(e, EXCEPTION_MESSAGE);
      return myMainEntity;
    }
  }

  public void setMainEntity(Entity mainEntity) {
    myMainEntity = mainEntity;
  }

  public List<Entity> getAsList(){
    return myEntities;
  }

  public void changeAllCoordinates(double xChange, double yChange){
    for(Entity entity : myEntities) {
      if (!entity.equals(myMainEntity)) {
        entity.setX(entity.getX() - xChange);
        entity.setY(entity.getY()- yChange);
      }
    }
  }
}
