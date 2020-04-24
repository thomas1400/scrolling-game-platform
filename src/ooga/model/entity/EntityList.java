package ooga.model.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Custom data structure that is used to store and access entities
 * Implements Iterable<Entity></Entity> so that a for each loop can be used to iterate through the entities
 * @author Cayla Schuval
 */
public class EntityList implements Iterable<Entity> {
  private List<Entity> myEntities;
  private Entity myMainEntity;

  /**
   * Creates a new instance of an EntityList
   */
  public EntityList(){
    myEntities = new ArrayList<>();
  }

  /**
   * Adds a new entity to the EntityList
   * @param entityReceived entity to be added
   */
  public void addEntity(Entity entityReceived) {
    myEntities.add(entityReceived);
  }

  /**
   * Removes an entity from EntityList
   * @param entity entity to be removed
   */
  public void removeEntity(Entity entity) {
    myEntities.remove(entity);
  }

  /**
   * Adds all entities in an EntityList to this EntityList
   * @param entities EntityList of entities to be added
   */
  public void addAllEntities(EntityList entities){
    myEntities.addAll(entities.getAsList());
  }

  /**
   * Removes all entities in an EntityList from this EntityList
   * @param entities EntityList of entities to be removed
   */
  public void removeAllEntities(EntityList entities){
    myEntities.removeAll(entities.getAsList());
  }

  /**
   * Checks to see if the EntityList contains an entity
   * @param entity entity that is being checked if it is located in the EntityList
   * @return boolean stating if the entity is in the EntityList
   */
  public boolean contains(Entity entity) {
    return myEntities.contains(entity);
  }

  /**
   * Determines the size of the EntityList
   * @return int stating how many entities are in the EntityList
   */
  public int size(){
    return myEntities.size();
  }

  /**
   * @return an Iterator<Entity></Entity> to allow the EntityList to be iterated through
   */
  public Iterator<Entity> iterator() {
    return myEntities.iterator();
  }

  /**
   * Clears the EntityList
   */
  public void clear(){ myEntities.clear(); }


  /**
   * Sets the main entity to the given entity
   * @param mainEntity entity to be set as main entity
   */
  public void setMainEntity(Entity mainEntity) {
    myMainEntity = mainEntity;
  }

  /**
   * @return Entity associated as the main entity of the EntityList
   * if there is no main entity set, it sets the first entity in the EntityList to be the main entity
   */
  public Entity getMainEntity() {
    if (myMainEntity==null){
      setMainEntity(myEntities.get(0));
    }
    return myMainEntity;
  }

  /**
   * Returns the EntityList in the form of a list
   */
  public List<Entity> getAsList(){
    return myEntities;
  }

  /**
   * Changes the coordinates of all of the entities except for the main entity by provided changes
   * @param xChange double representing the amount to change by in the x direction
   * @param yChange double representing the amount to change by in the y direction
   */
  public void changeAllCoordinates(double xChange, double yChange){
    for(Entity entity : myEntities) {
      if (!entity.equals(myMainEntity)) {
        entity.setX(entity.getX() - xChange);
        entity.setY(entity.getY()- yChange);
      }
    }
  }
}
