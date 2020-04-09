package ooga.engine.manager;

import java.util.List;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;
import ooga.utility.observer.Observable;
import ooga.utility.observer.Observer;


public class CollisionManager implements Observable {
  private static final String SIDE = "Side";
  private static final String TOP = "Top";
  private static final String BOTTOM = "Bottom";


  private List<Observer> observers;

  public void manageCollisions(EntityList entities) {
    for(Entity entity:entities){
      for(Entity entity2:entities){
        if(entity.getBoundsInLocal().intersects(entity2.getBoundsInLocal())) {
          if (entity.getBoundsInLocal().getMaxX() == entity2.getBoundsInLocal().getMinX()
              || entity2.getBoundsInLocal().getMaxX() == entity.getBoundsInLocal().getMinX()) {
            //createAndSendCollision(SIDE, entity2,getName(), entity);
            //createAndSendCollision(SIDE, entity,getName(), entity2);
          } else if (entity.getBoundsInLocal().getMaxY() == entity2.getBoundsInLocal().getMinY()) {
            //createAndSendCollision(TOP, entity2,getName(), entity);
            //createAndSendCollision(BOTTOM, entity,getName(), entity2);
          } else {
            //createAndSendCollision(BOTTOM, entity2,getName(), entity);
            //createAndSendCollision(TOP, entity,getName(), entity2);
          }
        }
      }
    }
  }

  private void createAndSendCollision(String typeOfCollision, String entityCollidedWith, Entity entity){
    //entity.receiveCollision(new CollisionEvent(typeOfCollision, entityCollidedWith));
  }




  //NOT SURE IF THESE WILL BE IMPLEMENTED LATER
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void notifyObservers() {

  }

}
