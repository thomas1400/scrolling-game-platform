package ooga.model.ability;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import ooga.exceptions.ExceptionFeedback;

/**
 * Exists to keep track of the lives of an Entity. Handles being hit, and dying.
 */
public class Health extends Ability {

  private static final String RESOURCE_PACKAGE = "abilitytypes/Health";
  private static final int DEATH = 0;
  private static final double SINGLE_LIFE = 1;

  private double myLives;
  private boolean immortal;

  /**
   * Health constructor
   * Looks in the health properties file and assign the right number of lives to the ability
   * @param vitality type of health
   */
  public Health(String vitality){
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PACKAGE);
    //look in the health properties file, and get the number of lives associated with that vitality
    try{
      String key = resources.getString(vitality);
      myLives = Integer.parseInt(key);
    } catch(MissingResourceException e) {
      ExceptionFeedback.throwBreakingException(e, "Bad vitality! check you've picked \"Weak\", \"Average\", or \"Strong\" in the entity file");
    }
    immortal = false;
  }

  /**
   * Constructor to create immortal objects
   */
  public Health(){
    immortal = true;
    myLives = DEATH;
    //todo if isDead breaks than change to myLives = 1;
  }

  /**
   * Checks to see if the health is 'empty'
   * @return if the entity is immortal or myLives is equal to zero
   */
  public boolean isDead(){
    return (myLives <= DEATH && !immortal);
  }
  //todo if health suddenly stops working change <= to ==


  /**
   * Change number of lives by the incoming value
   * @param lives value to change lives by
   */
  public void addLives(double lives){
    myLives+=lives;
  }

  /**
   * Set the lives to the incoming value
   * @param newLivesNum new number of total lives
   */
  public void setLives(double newLivesNum){
    myLives=newLivesNum;
  }

  /**
   * Decreases the number of lives by one if the object is not dead
   */
  public void damage(){
    if(!immortal && !this.isDead()) {
      myLives -= SINGLE_LIFE;
    }
  }

  //todo delete when finished debugging
  public double getLives(){
    return myLives;
  }

  @Override
  public String toString(){
    return "Health";
  }
}
