package ooga.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.ability.Ability;
import ooga.model.ability.Health;
import ooga.model.ability.Movement;
import ooga.model.ability.Stunnable;
import ooga.model.ability.attacktypes.Attack;
import ooga.model.behavior.Collidible;
import ooga.model.behavior.CollisionBehaviorBundle;
import ooga.model.physics.Physics;
import ooga.utility.event.CollisionEvent;

public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private CollisionBehaviorBundle cbb;
  private Physics myPhysics;
  private Health health;
  private Movement movement;
  private Stunnable stunType;
  private Attack side, top, bottom;
  private List<Ability> myAbilities;
  private String debuggingName;
  private boolean dead;

  /**
   * Create default health and attacks, which can be overwritten later
   * using the addAbility method
   */
  public Entity(Image image, String name){
    super(image);
    debuggingName = name;
    myAbilities = new ArrayList<Ability>();
    health = new Health();
    side = Attack.HARMLESS;
    top = Attack.HARMLESS;
    bottom = Attack.HARMLESS;
    stunType = new Stunnable();
  }

  public void updateAttack(String location, String attackType) {
    Attack attack = Attack.HARMLESS;
    if (attackType.equals("BOUNCE")){
      attack = Attack.BOUNCE;
    } else if (attackType.equals("STUN")){
      attack = Attack.STUN;
    } else if (attackType.equals("DAMAGE")){
      attack = Attack.DAMAGE;
    }
    //todo learn how to make enums with reflection and change the above to that

    try {
      Method method = Entity.class.getDeclaredMethod("add"+location, Attack.class);
      method.invoke(Entity.this, attack);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void addAbility(String abilityType, Ability ability){
    //myAbilities.add(a);
    //makeMethod("add"+abilityType);
    try {
      Method method = Entity.class.getDeclaredMethod("add"+abilityType, Ability.class);
      method.invoke(Entity.this, ability);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //used for reflection DO NOT DELETE
  private void addHealth(Ability h){
    health = (Health) h;
  }

  //used for reflection DO NOT DELETE
  private void addStunnable(Ability s){
    stunType = (Stunnable) s;
  }

  //used for reflection DO NOT DELETE
  private void addSideAttack(Attack a){
    side = a;
  }

  //used for reflection DO NOT DELETE
  private void addTopAttack(Attack a){
    top = a;
  }

  //used for reflection DO NOT DELETE
  private void addBottomAttack(Attack a){
    bottom = a;
  }

  @Override
  public String[] getTags() {
    return new String[0];
  }

  public boolean isDead(){
    return dead;
  }

  /**
   * Get the attack of the specific side
   * @param location
   * @return call method to get the attack of the specific location
   */
  public Attack getAttack(String location){
    try {
      Method method = Entity.class.getDeclaredMethod("get"+location+"Attack");
      return (Attack) method.invoke(Entity.this);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //used for reflection DO NOT DELETE
  private Attack getSideAttack(){
    /*if(stun.isStunned()){
      side = Attack.DAMAGE;
    }*/
    return side;
  }

  //used for reflection DO NOT DELETE
  private Attack getTopAttack(){
    return top;
  }

  //used for reflection DO NOT DELETE
  private Attack getBottomAttack(){
    return bottom;
  }

  @Override
  public Entity handleCollision(CollisionEvent ce) {
    String location = ce.getCollisionLocation();
    Attack otherAttack = ce.getAttackType();

    if(debuggingName.equals("Mario.png")){
      System.out.println("Collision on "+location+"! other has attack type: "+otherAttack.toString());
    }
    Attack myAttack = this.getAttack(location);
    if(!myAttack.equals(otherAttack)) {
      try {
        Method method = Entity.class.getDeclaredMethod(otherAttack.toString(), Attack.class);
        method.invoke(Entity.this, myAttack);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
    return this;
  }

  //used for reflection DO NOT DELETE
  private void damage(Attack myAttack){
    health.hit();
    dead = health.isDead();
  }


  //used for reflection DO NOT DELETE
  private void stun(Attack myAttack){
    if(stunType.isStunnable()) {
      stunType.setStunned(true);
      side = Attack.HARMLESS;
      top = Attack.BOUNCE;
    } else {
      damage(myAttack);
    }
  }

  //used for reflection DO NOT DELETE
  private void bounce(Attack myAttack){
    //if it's on the bottom
    movement.jump();
    //if it's side
    /*if(stun.isStunned()){
      side
    }*/
  }

  //used for reflection DO NOT DELETE
  private void nothing(Attack myAttack){
    //do nothing
  }

  @Override
  public void updateVisualization() {
    //todo this is the moving thing?
  }

  public Renderable getRenderable() {
    return null;
  }

  public Manageable getManageable() {
    return null;
  }
}
