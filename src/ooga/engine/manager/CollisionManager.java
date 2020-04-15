package ooga.engine.manager;

import java.util.List;
import ooga.model.ability.attacktypes.Attack;
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
  private EntityList entitiesReceived;

  public void manageCollisions(EntityList entities) {
    entitiesReceived = new EntityList();
    for (Entity entity : entities) {
      for (Entity entity2 : entities) {
        if (!entity.equals(entity2)) {
          if (entity.getBoundsInLocal().intersects(entity2.getBoundsInLocal())) {
            //check that the sides overlap, and that one entity 'box' envelops another.
            if (((entity.getBoundsInLocal().getMaxX() > entity2.getBoundsInLocal().getMinX()) || entity2.getBoundsInLocal().getMaxX() > entity.getBoundsInLocal().getMinX())
                && (entity.getBoundsInLocal().getMinY() >= entity2.getBoundsInLocal().getMinY() && entity.getBoundsInLocal().getMaxY() <= entity2.getBoundsInLocal().getMaxY())){
              //printDebug(SIDE, entity, entity2);
              createAndSendCollision(SIDE, entity2.getAttack(SIDE), entity);
              createAndSendCollision(SIDE, entity.getAttack(SIDE), entity2);
            } else if (entity.getBoundsInLocal().getMaxY() <= entity2.getBoundsInLocal()
                .getMinY()) {
              //printDebug(TOP, entity, entity2);
              //printDebug(BOTTOM, entity2, entity);
              createAndSendCollision(TOP, entity2.getAttack(TOP), entity);
              createAndSendCollision(BOTTOM, entity.getAttack(BOTTOM), entity2);
            } else {
              //printDebug(BOTTOM, entity, entity2);
              //printDebug(TOP, entity2, entity);
              createAndSendCollision(BOTTOM, entity2.getAttack(BOTTOM), entity);
              createAndSendCollision(TOP, entity.getAttack(TOP), entity2);
            }
          }
        }
      }
    }
  }

  //fixme delete after debugging over
  private void printDebug(String loc, Entity a, Entity b){
    if((a.debug().equals("Mario.png")||b.debug().equals("Mario.png"))){// && (!a.debug().contains("Ground")&&!b.debug().contains("Ground"))) {
      System.out.println("a max x: "+ a.getBoundsInLocal().getMaxX());
      System.out.println("a min x: " + a.getBoundsInLocal().getMinX());
      System.out.println("b max x: "+ b.getBoundsInLocal().getMaxX());
      System.out.println("b min x: " + b.getBoundsInLocal().getMinX());
      System.out.println("a max y: "+ a.getBoundsInLocal().getMaxY());
      System.out.println("a min y: " + a.getBoundsInLocal().getMinY());
      System.out.println("b max y: "+ b.getBoundsInLocal().getMaxY());
      System.out.println("b min y: " + b.getBoundsInLocal().getMinY());
      System.out.println("Me: " + a.debug() + " && other:" + b.debug());
      System.out.println("ATTACK TYPES: me: " + a.getAttack(loc) + " other: " + b.getAttack(loc));
    }
  }

  private void createAndSendCollision(String typeOfCollision, Attack attack, Entity entity){
    //receive an entity object from the entity
    entitiesReceived.addEntity(entity.handleCollision(new CollisionEvent(typeOfCollision, attack)));
  }

  public EntityList getEntitiesReceived(){
    return entitiesReceived;
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
