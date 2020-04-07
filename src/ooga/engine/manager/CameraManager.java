package ooga.engine.manager;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {
  Entity mainCharacter;
  int xCenter;
  int yCenter;
  int screenWidth;
  int screenHeight;
  EntityList activatedEntities;
  EntityList deactivatedEntities;

  public CameraManager(Entity character){
    character = mainCharacter;
  }

  protected void manageCamera(EntityList entities){
    if(mainCharacter.getX()!=xCenter | mainCharacter.getY()!=yCenter){
      double xChange = mainCharacter.getX()-xCenter;
      double yChange = mainCharacter.getY()-yCenter;
      mainCharacter.setX(xCenter);
      mainCharacter.setY(yCenter);
      /*for(Entity entity : entities){
        if (entity!=mainCharacter){
          entity.setX(entity.getX()- xChange);
          entity.setY(entity.getY()- yChange);
        }
      }*/
      
    }

  }

  private void updateCoordinates(){

  }

  public void activateEntities(EntityList entities){
    /*for (Entity entity : entities){
      if(entity.getX()<screenWidth && entity.getY()<screenHeight){
        //entity.activate();
      }
      else if (){)
    }

    }*/
  }

  public EntityList getActivatedEntities(){
    return activatedEntities;
  }
  public EntityList getDeactivatedEntities(){
    return deactivatedEntities;
  }
  public void resetActivationStorage(){}


}
