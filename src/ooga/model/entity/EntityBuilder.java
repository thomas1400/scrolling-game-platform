package ooga.model.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import ooga.model.ability.Ability;

public class EntityBuilder {

  private static final String ABILITY_PACKAGE = "ooga.model.ability.";
  private static final String STATS_PACKAGE_NAME = "gamedata/mario/entities/";
  public static final String IMAGE_KEY = "Image";

  //private ResourceBundle resources;
  //private Entity entity;


  //TODO take out throwing runtime exceptions, throw actual ones
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
      System.out.println("ClassNotFoundException");
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException");
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      System.out.println("NoSuchMethodException");
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      System.out.println("InstantiationException");
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      System.out.println("InvocationTargetException");
      throw new RuntimeException(e);
    }
  }

  //TODO check that file path is valid
  /**
   * Getter for created entity
   * Must be passed in the proper strings following the format of an
   * ability building file, where keys are valid ability classes
   * and values are valid ability types
   * @param statsFilename file name for the entity stat resource file
   * @return created entity
   */
  public static Entity getEntity(String statsFilename) {
    try {
      ResourceBundle resources = ResourceBundle.getBundle(STATS_PACKAGE_NAME + statsFilename);
      //System.out.println(resources.getString(IMAGE_KEY));
      Image image = new Image("images/entityimages/marioimages/" + resources.getString(IMAGE_KEY));
      Entity entity = new Entity(image, resources.getString("Image"));

      for (String s : Collections.list(resources.getKeys())) {
        //todo remove this if?
        if (!s.equals("Image")) {
          //reflection!
          if (s.contains("Attack")) {
            entity.updateAttack(s, resources.getString(s));
          } else {
            Ability a = makeAbility(s, resources.getString(s));
            entity.addAbility(s, a);
          }
        }
      }
      return entity;
    } catch (MissingResourceException e){
      System.out.println("You didn't edit the level file correctly. Can't find the properties file for a type! Either add the file or remove the type from the level");
      //todo add which type it is
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    EntityBuilder eb = new EntityBuilder();
    eb.getEntity("Player");
  }
}
