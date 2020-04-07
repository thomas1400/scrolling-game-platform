package ooga.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.event.Event;
import ooga.utility.observer.Observer;

public class EntityList implements Observer {

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

  public Entity getMainCharacter() {

    return myMainCharacter;
  }

  public List<Entity> getAsList(){
    List<Entity> entityList = new ArrayList<Entity>();
    return entityList;
  }
}
