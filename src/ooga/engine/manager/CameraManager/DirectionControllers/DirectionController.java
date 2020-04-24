package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.model.entity.EntityList;

/**
 * Abstract class that establishes that all DirectionController objects must have the below methods
 * The methods contained in this super class are called by the camera manager to update the positions of the entities based upon the specif direction controller object
 * @author Cayla Schuval
 */
abstract public class DirectionController {

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param screenHeight screen height of the game used to determine if entities move on or off screen
   * @param screenWidth screen width of the game used to determine if entities move on or off screen
   * checks the position of the main entity and updates all entity positions respectively
   */
  public abstract void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth);

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param screenHeight screen height of the game used to determine if entities move on or off screen
   * @param screenWidth screen width of the game used to determine if entities move on or off screen
   * sets the initial position off all of the entities based upon the location of the main entity
   */
  public abstract void initialize(EntityList entities, double screenHeight, double screenWidth);
}
