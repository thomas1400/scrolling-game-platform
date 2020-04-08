package ooga.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.event.Event;
import ooga.utility.observer.Observer;

public class EntityList implements Iterable<Entity>, Observer {

  public EntityList(){
    setMyMainCharacter();
  }

  private void setMyMainCharacter(){
    /*for(Entity entity : this) {
      if (entity.isMainCharacter()) {
        myMainCharacter = entity;
      }
    }*/
  }

  private Collection<Entity> myEntities;
  private Entity myMainCharacter;

  public boolean contains(Object o) {
    return false;
  }

  public Iterator<Entity> iterator() {
    return null;
  }

  @Override
  public void handleNotification(Event e) {

  }

  public void removeEntity(Entity entity) {
  }

  public void addEntity(Entity entityReceived) {
  }

  public Entity getMainEntity() {

    return myMainCharacter;
  }

  public void addMainEntity(Entity mainEntity) {
    myMainCharacter = mainEntity;
  }

  public List<Entity> getAsList(){
    List<Entity> entityList = new ArrayList<Entity>();
    return entityList;
  }
  public void changeAllCoordinates(double xChange, double yChange){
    for(Entity entity : this) {
      if (entity != myMainCharacter) {
        entity.setX(entity.getX() - xChange);
        entity.setY(entity.getY() - yChange);
      }
    }
  }
}
