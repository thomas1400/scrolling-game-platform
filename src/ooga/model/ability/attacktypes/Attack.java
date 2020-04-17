package ooga.model.ability.attacktypes;

public enum Attack {

  HARMLESS("Nothing"),
  COLLECT("Collect"),
  COLLECTIBLE("Collectible"),
  DAMAGE("Damage"),
  STUN("Stun"),
  SUPPORT("Support"),
  BOUNCE("Bounce");

  private String attackType;

  Attack(String type){
    attackType = type;
  }

  @Override
  public String toString(){
    return attackType;
  }
}
