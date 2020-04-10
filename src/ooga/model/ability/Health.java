package ooga.model.ability;

import java.util.ResourceBundle;

/**
 * Exists to keep track of the lives of an Entity. Handles being hit, and dying.
 */
public class Health extends Ability {

  private static final String RESOURCE_PACKAGE = "resources.abilitytypes.Health";

  private int myLives;
  private boolean immortal;

  /**
   * Health constructor
   * Looks in the health properties file and assign the right number of lives to the ability
   * @param vitality type of health
   */
  public Health(String vitality){
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PACKAGE);
    //look in the health properties file, and get the number of lives associated with that vitality
    myLives = Integer.parseInt(resources.getString(vitality));
    immortal = false;
  }

  public Health(){
    immortal = true;
    //todo if isDead breaks than add myLives = 1;
  }

  /**
   * Checks to see if the health is 'empty'
   * @return if the entity is immortal or myLives is equal to zero
   */
  public boolean isDead(){
    return (immortal || myLives == 0);
  }

  /**
   * Decreases the number of lives by one if the object is not dead
   */
  public void hit(){
    if(!immortal && !this.isDead()) {
      myLives -= 1;
    }
  }
}
