package ooga.model.ability;

public class Stunnable extends Ability {

  private boolean stunnable;
  private boolean stunned;

  public Stunnable(String stunAbility){
    if(stunAbility.equals("true") || stunAbility.equals("on") || stunAbility.equals("yes")){
      stunnable = true;
    } else {
      stunnable = false;
    }
    stunned = false;
  }

  public Stunnable(){
    this("off");
  }

  public boolean isStunnable(){
    return stunnable;
  }

  public void setStunned(boolean b){
    stunned = b;
  }

  public boolean isStunned(){
    return stunned;
  }
}
