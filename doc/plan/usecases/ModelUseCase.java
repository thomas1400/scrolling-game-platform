/**
 * @author Dana Mulligan
 */

public class ModelUseCase {

  /**
   * This code should be able to be run inside of player,
   * assuming the jump method has been written.
   */
  public static void main(String[] args) {
    Player mainCharacter = new Player();
    try{
      mainCharacter.jump(); //where jumpTime is a instance variable determined by the loaded character
    } catch (Exception e){
      ExceptionFeedback.throw(e, "This player cannot jump!");
    }
  }
}
