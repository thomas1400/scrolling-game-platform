package ooga.model.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.model.ResourceParser;
import ooga.model.ability.Ability;

public class EntityBuilder {

  private static final String ABILITY_PACKAGE = "ooga.model.ability.";

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
   * @param statsPackageName where to find the file in the file hierarchy
   * @param statsFilename file name for the entity stat resource file
   * @return created entity
   */
  public static Entity getEntity(String statsPackageName, String statsFilename){
    Entity entity = new Entity();
    ResourceParser parser = new ResourceParser(statsPackageName, statsFilename);
    entity = new Entity();
    for(String s : parser.getKeys()){
      //reflection!
      Ability a = makeAbility(s, parser.getSymbol(s));
      entity.addAbility(a);
    }
    return entity;
  }

}
