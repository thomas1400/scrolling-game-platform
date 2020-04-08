package ooga.model.ability;

import ooga.model.ResourceParser;

/**
 * Exists to keep track of the lives of an Entity. Handles being hit, and dying.
 */
public class Health extends Ability {

  private static final int DEFAULT_LIVES = 1;
  private static final String RESOURCE_PACKAGE = "resources.abilitytypes.";
  private static final String HEALTH_FILE = "Health.properties";

  private int myLives;

  /**
   * Health constructor
   * Looks in the health properties file and assign the right number of lives to the ability
   * @param vitality type of health
   */
  public Health(String vitality){
    ResourceParser parser = new ResourceParser(RESOURCE_PACKAGE, HEALTH_FILE);
    //look in the health properties file, and get the number of lives associated with that vitality
    myLives = Integer.parseInt(parser.getSymbol(vitality));
  }

  /**
   * Checks to see if the health is 'empty'
   * @return if myLives is equal to zero
   */
  public boolean isDead(){
    return myLives == 0;
  }

  @Override
  /**
   * Decreases the number of lives by one if the object is not dead
   */
  public void hit(){
    if(!this.isDead()) {
      myLives -= 1;
    }
  }
}
