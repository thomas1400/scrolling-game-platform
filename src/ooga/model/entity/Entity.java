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
import ooga.model.ability.attacktypes.Attack;
import ooga.model.behavior.Collidible;
import ooga.model.physics.Physics;
import ooga.utility.event.CollisionEvent;

public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private static final String HARMLESS = "Harmless";

  private Health health;
  private Movement movement;
  private CollectiblePackage myPackage;
  private String side, top, bottom;
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
    side = HARMLESS;
    top = HARMLESS;
    bottom = HARMLESS;
    haveMovement = false;
    myPackage = new CollectiblePackage("nothing 0");
    score = 0;
    scale = 1;
    //todo take out magic vals
  }

  public void updateAttack(String location, String attackType) {
    /*Attack attack = Attack.HARMLESS;
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
*/
    try {
      Method method = Entity.class.getDeclaredMethod("add"+location, String.class);
      method.invoke(Entity.this, attackType);
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
  public void addCollectiblePackage(Ability p){
    myPackage = (CollectiblePackage) p;
  }

  //used for reflection DO NOT DELETE
  private void addMovement(Ability m){
    movement = (Movement) m;
    haveMovement = true;
  }

  //used for reflection DO NOT DELETE
  private void addSideAttack(String a){
    side = a;
  }

  //used for reflection DO NOT DELETE
  private void addTopAttack(String a){
    top = a;
  }

  //used for reflection DO NOT DELETE
  private void addBottomAttack(String a){
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
  public String getAttack(String location){
    try {
      Method method = Entity.class.getDeclaredMethod("get"+location+"Attack");
      return (String) method.invoke(Entity.this);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //used for reflection DO NOT DELETE
  private String getSideAttack(){
    return side;
  }

  //used for reflection DO NOT DELETE
  private String getTopAttack(){
    return top;
  }

  //used for reflection DO NOT DELETE
  private String getBottomAttack(){
    return bottom;
  }

  @Override
  public Entity handleCollision(CollisionEvent ce) {
    String location = ce.getCollisionLocation();
    String otherAttack = ce.getAttackType();
    String myAttack = this.getAttack(location);

    try {
      ResourceBundle myAttackSpecificResponseBundle = ResourceBundle.getBundle("entities/collisions/"+otherAttack.toString());
      String[] methodsToCall = myAttackSpecificResponseBundle.getString(myAttack).split(" ");
      for(String s : methodsToCall) {
        Method method = Entity.class.getDeclaredMethod(s);
        method.invoke(Entity.this);
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
  private void damage(){
    health.hit();
    dead = health.isDead();
  }

  //used for reflection DO NOT DELETE
  /**
   * note: the other one is the collectible item
   */
  private void collectMe(){
    //todo create a bonus ability that can change score, height, etc. have that happen here as the entity is collected
    String methodToCall = myPackage.toString();
    double value = myPackage.getPackageValue();
    if(!dead){
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
  }

  //used for reflection DO NOT DELETE
  private void points(Double value){
    score += value;
    System.out.println("score: " + score);
  }

  //used for reflection DO NOT DELETE
  private void size(Double value){
    scale = value;
  }

  //used for reflection DO NOT DELETE
  private void levelEnd(Double value){
    System.out.println("we did it");
    levelEnded = true;
    success = (value!=0);
  }

  //used for reflection DO NOT DELETE
  private void collect(){
    //do nothing
  }

  //used for reflection DO NOT DELETE
  private void supportY(){
    if(haveMovement) {
      //setY(getY()-Physics.TINY_DISTANCE);
      movement.standY();
    }
  }

  //used for reflection DO NOT DELETE
  private void supportX(){
    if(haveMovement) {
      //setY(getY()-Physics.TINY_DISTANCE);
      movement.standX();
    }
  }

  //used for reflection DO NOT DELETE
  private void bounceY(){
    if(haveMovement) {
      movement.bounceY();
    }
  }

  //used for reflection DO NOT DELETE
  private void bounceX(){
    if(haveMovement) {
      movement.bounceX();
    }
  }

  //used for reflection DO NOT DELETE
  private void nothing(){
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
  //used for reflection DO NOT DELETE
  public boolean endedLevel(){
    return levelEnded;
  }

  //used for reflection DO NOT DELETE
  public boolean isSuccess(){
    return success;
  }

  //used for reflection DO NOT DELETE
  public void setScore(double newScore){
    score = newScore;
  }
  //used for reflection DO NOT DELETE
  public double getScore(){
    return score;
  }

  //used for reflection DO NOT DELETE
  public void moveRight(){
    setScaleX(1);
    movement.right();
  }

  //used for reflection DO NOT DELETE
  public void moveLeft(){
    setScaleX(-1);
    movement.left();
  }

  //used for reflection DO NOT DELETE
  public void jump(){
    setY(getY()-Physics.TINY_DISTANCE);
    movement.jump();
  }
}
