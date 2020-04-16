package ooga.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.ability.Ability;
import ooga.model.ability.CollectiblePackage;
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
  private boolean dead, haveMovement;

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
    myPhysics = new Physics();
    haveMovement = false;
  }

  public void updateAttack(String location, String attackType) {
    Attack attack = Attack.HARMLESS;
    if (attackType.equals("BOUNCE")){
      attack = Attack.BOUNCE;
    } else if (attackType.equals("STUN")){
      attack = Attack.STUN;
    } else if (attackType.equals("DAMAGE")){
      attack = Attack.DAMAGE;
    } else if (attackType.equals("SUPPORT")){
      attack = Attack.SUPPORT;
    } else if (attackType.equals("COLLECT")){
      attack = Attack.COLLECT;
    } else if (attackType.equals("COLLECTIBLE")){
      attack = Attack.COLLECTIBLE;
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

  public void addCollectiblePackage(String packageType){
    CollectiblePackage pac = CollectiblePackage.DEFAULT;
    if(packageType.equals("POINTS")){
      pac = CollectiblePackage.POINTS;
    } else if (packageType.equals("SIZE")){
      pac = CollectiblePackage.SIZE;
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

  private void addMovement(Ability m){
    movement = (Movement) m;
    haveMovement = true;
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

  public String debug(){
    if(debuggingName.equals("Mario.png")){
     // System.out.println("Side: "+side.toString()+" Bottom: "+bottom.toString()+" top: "+top.toString());
    }
    return debuggingName;
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
        //System.out.println(otherAttack);
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
  /**
   * note: the other one is the collectible item
   */
  private void collectible(Attack myAttack){
    //todo create a bonus ability that can change score, height, etc. have that happen here as the entity is collected
  }

  //used for reflection DO NOT DELETE
  private void collect(Attack myAttack){
    damage(myAttack);
    //fixme change this if you add anything to damage()
    //todo implement bonuses here instead of in collectable?
  }

  //used for reflection DO NOT DELETE
  private void support(Attack myAttack){
    if(haveMovement) {
      movement.stand();
    }
  }

  //used for reflection DO NOT DELETE
  private void stun(Attack myAttack){
    if(stunType.isStunnable()) {
      stunType.setStunned(true);
      side = Attack.HARMLESS;
      top = Attack.BOUNCE;
    } else {
      //damage(myAttack);
    }
  }

  //used for reflection DO NOT DELETE
  private void bounce(Attack myAttack){
    //if it's on the bottom
    System.out.println(debuggingName);
    if(haveMovement) {
      movement.bounceX();
    }
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
    if(haveMovement) {
      movement.update(this);
    }
    dead = health.isDead();
  }

  public void moveRight(){
    movement.right();
  }

  public void moveLeft(){
    movement.left();
  }

  public void jump(){
    System.out.println("hi");
    movement.jump();
  }

}
