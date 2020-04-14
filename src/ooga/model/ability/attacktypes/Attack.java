package ooga.model.ability.attacktypes;

public enum Attack {

  HARMLESS("nothing"),
  COLLECT("collect"),
  COLLECTIBLE("collectible"),
  DAMAGE("damage"),
  STUN("stun"),
  SUPPORT("support"),
  BOUNCE("bounce");

  private String attackType;

  Attack(String type){
    attackType = type;
  }

  @Override
  public String toString(){
    return attackType;
  }
}
