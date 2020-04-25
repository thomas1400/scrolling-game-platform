package ooga.model.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.ability.Ability;
import ooga.model.ability.Health;

public class EntityBuilder {

  private static final String ABILITY_PACKAGE = "ooga.model.ability.";
  private static final String IMAGE_KEY = "Image";
  private static final String GAME_DATA_FOLDER = "gamedata/";
  private static final String ENTITY_FOLDER = "/entities/";
  private static final String BEHAVIOR_FOLDER = "behavior/";
  private static final String IMAGE_FOLDER = "images/";
  private static final String SPLIT = ",";
  private static final String USER_INPUT_INFORMATION = "/entities/entities";
  private static final String CLASS_ERROR = "Can't find the class to make the Ability";
  private static final String ILLEGAL_ERROR = "Can't access the class to make the Ability";
  private static final String INVOKE_ERROR = "Can't invoke the ability";
  private static final String INSTANTIATION_ERROR = "Can't instantiate the ability";
  private static final String CONSTRUCTOR_ERROR = "No constructor to make the ability";
  private static final String MISSING_RESOURCE_ERROR = "You didn't edit the level file correctly. Can't find the properties file for a type! Either add the file or remove the type from the level";
  private static ResourceBundle myEntityResources;
  private static final String ATTACK = "Attack";
  private static final int ATTACK_LOCATION = 0;
  private static final int ENTITY_LOCATION = 0;
  private static final int IMAGE_LOCATION = 1;

  /**
   * reflection to create the Ability object. Assumes that the stats string is an integer
   * @param abilityType specific Ability object to create
   * @param stats String containing the specific type of the sub Ability
   * @return created Ability
   */
  private static Ability makeAbility(String abilityType, String stats){
    try{
      Class abilityClass = Class.forName(ABILITY_PACKAGE + abilityType);
       Constructor abilityClassConstructor = abilityClass.getConstructor(String.class);
      return (Ability) abilityClassConstructor.newInstance(stats);
    } catch (ClassNotFoundException e){
      ExceptionFeedback.throwBreakingException(e, CLASS_ERROR);
    } catch (IllegalAccessException e) {
      ExceptionFeedback.throwBreakingException(e, ILLEGAL_ERROR);
    } catch (NoSuchMethodException e) {
      ExceptionFeedback.throwBreakingException(e, CONSTRUCTOR_ERROR);
    } catch (InstantiationException e) {
      ExceptionFeedback.throwBreakingException(e, INSTANTIATION_ERROR);
    } catch (InvocationTargetException e) {
      ExceptionFeedback.throwBreakingException(e, INVOKE_ERROR);
    }
    return new Health(); //had to add because of how our exceptions are thrown
  }

  /**
   * Getter for created entity
   * Must be passed in the proper strings following the format of an
   * ability building file, where keys are valid ability classes
   * and values are valid ability types
   * @param statsFilename file name for the entity stat resource file
   * @return created entity
   */
  public static Entity getEntity(String statsFilename, String gameType) {
    String UserInputResources = GAME_DATA_FOLDER+gameType+USER_INPUT_INFORMATION;
    myEntityResources = ResourceBundle.getBundle(UserInputResources);
    String[] entityInformation = getEntityInfo(statsFilename);
    String entityType = entityInformation[ENTITY_LOCATION];
    String imageFile = entityInformation[IMAGE_LOCATION];
    try {
      return createEntity(gameType, entityType, imageFile);
    } catch (MissingResourceException e){
      ExceptionFeedback.throwHandledException(new RuntimeException(), MISSING_RESOURCE_ERROR);
      throw new RuntimeException();
    }
  }

  private static Entity createEntity(String gameType, String entityType, String imageFile) {
    String gameSpecificFilePath = GAME_DATA_FOLDER + gameType + ENTITY_FOLDER;
    ResourceBundle resources = ResourceBundle.getBundle(gameSpecificFilePath + BEHAVIOR_FOLDER + entityType);
    Image image = new Image(gameSpecificFilePath + IMAGE_FOLDER + imageFile);
    Entity entity = new Entity(image, imageFile, gameType);

    updateEntity(resources, entity);
    return entity;
  }

  private static void updateEntity(ResourceBundle resources, Entity entity) {
    for (String s : Collections.list(resources.getKeys())) {
      if (!s.equals(IMAGE_KEY) && s.contains(ATTACK)) {
        entity.updateAttack(s.split(ATTACK)[ATTACK_LOCATION], resources.getString(s));
      } else {
        Ability a = makeAbility(s, resources.getString(s));
        entity.addAbility(s, a);
      }
    }
  }

  private static String[] getEntityInfo(String entityCode){
    return myEntityResources.getString(entityCode).split(SPLIT);
  }
}
