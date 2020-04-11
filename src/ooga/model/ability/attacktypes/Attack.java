package ooga.model.ability.attacktypes;

public enum Attack {

  HARMLESS("nothing"),
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
