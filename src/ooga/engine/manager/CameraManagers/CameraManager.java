package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public abstract class CameraManager {

    protected Entity mainEntity;
    protected double screenHeight;
    protected double screenWidth;
    protected double change;
    private EntityList activatedEntities;
    private EntityList deactivatedEntities;
    private EntityList onScreenEntities;

    public CameraManager(Entity character, double height, double width) {
      mainEntity = character;
      screenHeight = height;
      screenWidth = width;
    }

    abstract public void updateCamera(EntityList entities);

    abstract public void updateCoordinates(EntityList entities);

    abstract public void resetMainEntityToCenter();

    public void initializeActivationStorage() {
      activatedEntities = new EntityList();
      deactivatedEntities = new EntityList();
    }

    public EntityList initializeActiveEntities(EntityList entities) {
      initializeActivationStorage();
      onScreenEntities = new EntityList();
      for (Entity entity : entities) {
        if (entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX()< screenWidth && entity.getBoundsInLocal().getMaxY() < screenHeight) {
          activatedEntities.addEntity(entity);
          onScreenEntities.addEntity(entity);
        }
      }
      return activatedEntities;
    }

    protected void determineEntitiesOnScreen(EntityList entities) {
      initializeActivationStorage();
      for (Entity entity : entities) {
        if (entityIsOnScreen(entity) && !onScreenEntities.contains(entity)) {
          activatedEntities.addEntity(entity);
          onScreenEntities.addEntity(entity);
        } else if ((!entityIsOnScreen(entity)) && onScreenEntities.contains(entity)) {
          onScreenEntities.removeEntity(entity);
          deactivatedEntities.addEntity(entity);
        }
      }
    }

    private boolean entityIsOnScreen(Entity entity){
      return entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMaxY()< screenHeight;
    }

    public EntityList getActivatedEntities(){
      return activatedEntities;
    }
    public EntityList getDeactivatedEntities(){
      return deactivatedEntities;
    }
    public EntityList getOnScreenEntities(){
      return onScreenEntities;
    }

}
