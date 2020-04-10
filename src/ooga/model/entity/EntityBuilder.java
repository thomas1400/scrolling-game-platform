package ooga.model.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import ooga.model.ability.Ability;

public class EntityBuilder {

  private static final String ABILITY_PACKAGE = "ooga.model.ability.";
  private static final String STATS_PACKAGE_NAME = "entities/";

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
    //ResourceParser parser = new ResourceParser(STATS_PACKAGE_NAME, statsFilename);
    //System.out.println("images/entityimages/"+parser.getSymbol("Image"));
    ResourceBundle resources = ResourceBundle.getBundle(STATS_PACKAGE_NAME + statsFilename);
    //System.out.println("images/entityimages/" + resources.getString("Image")); //fixme remove
    // print
    Image image = new Image("images/entityimages/" + resources.getString("Image"));
    Entity entity = new Entity(image);

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
  }

  public static void main(String[] args) {
    EntityBuilder eb = new EntityBuilder();
    eb.getEntity("Player");
  }
}
