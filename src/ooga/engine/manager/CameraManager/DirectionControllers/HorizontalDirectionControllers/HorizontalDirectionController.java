package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

abstract public class HorizontalDirectionController extends DirectionController {
  private double xCenter;
  private double change;
  private Entity myMainEntity;
  private double myScreenHeight;
  private double myScreenWidth;

  public void setToCenter(EntityList entities, int offset){
    xCenter = myScreenWidth / 2 - myMainEntity.getBoundsInLocal().getWidth() / 2;
    change = myMainEntity.getX() - xCenter - offset;
    resetMainEntity(offset);
    updateCoordinates(entities);
  }

  private void resetMainEntity(int offset){
    xCenter = myScreenWidth / 2 - myMainEntity.getBoundsInLocal().getWidth() / 2;
    myMainEntity.setX(xCenter+offset);

  }
  private void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(change, 0);
  }

  public void checkIfMarioTouchesTopOrBottomOfScreen(){
    if (myMainEntity.getY()<=0){
      myMainEntity.setY(0.1);
    }
    /*if (myMainEntity.getY()>=myScreenHeight){
      System.out.println("reset");
     resetMainEntity(getOffset());
    }*/
    //add touching bottom of screen
  }

  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    myMainEntity = mainEntity;
    if(mainEntity.getX()<0){
      setToCenter(entities, getOffset());
    }
    else if(mainEntity.getX()>screenWidth){
      setToCenter(entities, getOffset());
    }
  }

  abstract public int getOffset();
}
