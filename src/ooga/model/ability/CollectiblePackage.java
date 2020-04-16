package ooga.model.ability;

public enum CollectiblePackage {

  //should I use enums? or should I make subclasses?
  DEFAULT("doNothing"),
  POINTS("score"),
  SIZE("scale");

  private String packageType;

  CollectiblePackage(String type){
    packageType = type;
  }

  @Override
  public String toString(){
    return packageType;
  }

}
