package ooga.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.event.Event;
import ooga.utility.observer.Observer;

public class EntityList implements Iterable<Entity>, Observer {
  private List<Entity> myEntities;
  private Entity myMainEntity;


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

  public Iterator<Entity> iterator() {
      return myEntities.iterator();
  }

  @Override
  public void handleNotification(Event e) {

  }

  public Entity getMainEntity() {
    return myMainEntity;
  }

  public void setMainEntity(Entity mainEntity) {
    myMainEntity = mainEntity;
  }

  public List<Entity> getAsList(){
    return myEntities;
  }

  public void changeAllCoordinates(double xChange, double yChange){
    for(Entity entity : this) {
      if (entity != myMainEntity) {
        entity.setX(entity.getX() - xChange);
        entity.setY(entity.getY() - yChange);
      }
    }
  }
}
