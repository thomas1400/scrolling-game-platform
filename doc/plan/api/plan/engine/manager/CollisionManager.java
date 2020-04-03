package plan.engine.manager;

import java.util.List;
import ooga.model.entity.EntityList;
import ooga.utility.observer.Observable;
import ooga.utility.observer.Observer;

public class CollisionManager implements Observable {

  private List<Observer> observers;

  public void manageCollisions(EntityList entities) { }

  @Override
  public void addObserver(Observer o) {

  }

  @Override
  public void notifyObservers() {

  }
}
