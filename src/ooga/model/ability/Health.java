package ooga.model.ability;

/**
 * Exists to keep track of the lives of an Entity. Handles being hit, and dying.
 */
public class Health extends Ability {

  private static final int DEFAULT_LIVES = 1;

  private int myLives;

  /**
   * Health constructor
   * @param lives number to set myLives to
   */
  public Health(int lives){
    myLives = lives;
  }

  /**
   * Default Health constructor, gives 1 life
   */
  public Health(){
    this(DEFAULT_LIVES);
  }

  /**
   * Checks to see if the health is 'empty'
   * @return if myLives is equal to zero
   */
  public boolean isDead(){
    return myLives == 0;
  }

  /**
   * Decreases the number of lives by one if the object is not dead
   */
  public void hit(){
    if(!this.isDead()) {
      myLives -= 1;
    }
  }
}
