package ooga.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
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
  private Health health;
  private Movement movement;
  private Stunnable stunType;
  private CollectiblePackage myPackage;
  private Attack side, top, bottom;
  private List<Ability> myAbilities;
  private String debuggingName;
  private double score, scale;
  private boolean dead, haveMovement, levelEnded, success;

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
    haveMovement = false;
    myPackage = new CollectiblePackage("nothing 0");
    score = 0;
    scale = 1;
    //todo take out magic vals
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
  public void addCollectiblePackage(Ability p){
    myPackage = (CollectiblePackage) p;
  }

  //used for reflection DO NOT DELETE
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

    try {
      ResourceBundle myAttackSpecificResponseBundle = ResourceBundle.getBundle("entities/collisions/"+otherAttack.toString());
      String[] methodsToCall = myAttackSpecificResponseBundle.getString(myAttack.toString()).split(" ");
      for(String s : methodsToCall) {
        Method method = Entity.class.getDeclaredMethod(s, Attack.class);
        method.invoke(Entity.this, myAttack);
      }
    } catch (MissingResourceException e) {
      System.out.println("Couldn't find key in bundle I'm:"+ debuggingName+"; we're at: "+location);
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      //System.out.println(otherAttack);
      throw new RuntimeException(e);
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
  private void collectMe(Attack myAttack){
    //todo create a bonus ability that can change score, height, etc. have that happen here as the entity is collected
    String methodToCall = myPackage.toString();
    double value = myPackage.getPackageValue();
    try {
      Method method = Entity.class.getDeclaredMethod(methodToCall, Double.class);
      method.invoke(Entity.this, value);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //used for reflection DO NOT DELETE
  private void points(Double value){
    score += value;
    //System.out.println("score: " + score);
  }

  //used for reflection DO NOT DELETE
  private void size(Double value){
    scale = value;
  }

  //used for reflection DO NOT DELETE
  private void levelEnd(Double value){
    //System.out.println("we did it");
    levelEnded = true;
    success = (value!=0);
  }

  //used for reflection DO NOT DELETE
  private void collect(Attack myAttack){
    //do nothing
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
    if(haveMovement) {
      movement.bounceX();
    }
  }

  //used for reflection DO NOT DELETE
  private void nothing(Attack myAttack){
    //do nothing
  }

  @Override
  public void updateVisualization() {
    if (haveMovement) {
      movement.update(this);
    }
    this.setScaleX(scale);
    this.setScaleY(scale);
    dead = health.isDead();
  }

  public boolean endedLevel(){
    return levelEnded;
  }

  public boolean isSuccess(){
    return success;
  }

  //used for reflection DO NOT DELETE
  public void moveRight(){
    movement.right();
  }

  //used for reflection DO NOT DELETE
  public void moveLeft(){
    movement.left();
  }

  //used for reflection DO NOT DELETE
  public void jump(){
    setY(getY()-Physics.TINY_DISTANCE);
    movement.jump();
  }
}
