package plan.engine.manager;

import java.util.List;
import plan.model.entity.EntityList;
import plan.utility.observer.Observable;
import plan.utility.observer.Observer;
import plan.utility.observer.Observable;
import plan.utility.observer.Observer;


public class CollisionManager implements Observable {

  private List<Observer> observers;

  public void manageCollisions(EntityList entities) { }

  @Override
  public void addObserver(Observer o) {

  }

  @Override
  public void notifyObservers() {

  }
  
  private void detectCollisions(){};

  private void sendCollisions(){};
}
