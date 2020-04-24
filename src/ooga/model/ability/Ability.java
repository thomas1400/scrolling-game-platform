package ooga.model.ability;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.model.entity.Entity;

abstract public class Ability{

  //assumes that classPackageName ends in a '.'
  //TODO fix variable names they suck
  protected Object reflect(String classPackageName, String className){
    try{
      Class reflectedClass = Class.forName(classPackageName + className);
      Constructor classConstructor = reflectedClass.getConstructor();
      return classConstructor.newInstance();
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
}
