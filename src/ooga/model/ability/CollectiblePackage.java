package ooga.model.ability;

public class CollectiblePackage extends Ability {

  private static final int TYPE_LOCATION = 0;
  private static final int VALUE_LOCATION = 1;
  private String typeTag;
  private double packageValue;

  public CollectiblePackage(String information){
    String[] array = information.split(" ");
    //todo if array's size is zero, make value 0. if array's size is bigger than two throw an error
    typeTag = array[TYPE_LOCATION];
    packageValue = Double.parseDouble(array[VALUE_LOCATION]);
  }

  public double getPackageValue(){
    return packageValue;
  }

  @Override
  public String toString(){
    return typeTag;
  }
}
