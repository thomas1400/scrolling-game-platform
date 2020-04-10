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
  private Ability stun;
  private Attack side, top, bottom;
  private List<Ability> myAbilities;
  private boolean dead;

  /**
   * Create default health and attacks, which can be overwritten later
   * using the addAbility method
   */
  public Entity(Image image){
    super(image);
    myAbilities = new ArrayList<Ability>();
    health = new Health();
    side = Attack.HARMLESS;
    top = Attack.HARMLESS;
    bottom = Attack.HARMLESS;
    stun = new Stunnable();
    myPhysics = new Physics(this);
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

  private void addHealth(Ability h){
    health = (Health) h;
  }

  private void addStunnable(Ability s){
    stun = (Stunnable) s;
  }

  private void addSideAttack(Attack a){
    side = a;
  }

  private void addTopAttack(Attack a){
    top = a;
  }

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

  //used for reflection
  private Attack getSideAttack(){
    /*if(stun.isStunned()){
      side = Attack.DAMAGE;
    }*/
    return side;
  }

  //used for reflection
  private Attack getTopAttack(){
    return top;
  }

  //used for reflection
  private Attack getBottomAttack(){
    return bottom;
  }

  @Override
  public void handleCollision(CollisionEvent ce) {
    /*
    for (Ability a : myAbilities){
      a.hit();
    }*/


    /**
     * pseudo code time baby
     */
    /*
     if (other) instanceof (damage)
      health.damage
     if (other) instanceof (stun)
      this.stun
     if (other) instance of (bounce)
     moveable.bounce

     */
  }

  //used for reflection
  private void damage(){
    health.hit();
  }

  /*
  //used for reflection
  private void stun(){
    if(stunType.isStunnable()) {
      stunType.setStunned(true);
      side = Attack.HARMLESS;
      top = Attack.BOUNCE;
    } else {
      damage();
    }
  }*/

  //used for reflection
  private void bounce(){
    //if it's on the bottom
    movement.jump();
    //if it's side
    /*if(stun.isStunned()){
      side
    }*/
  }

  //used for reflection
  private void nothing(){
    //do nothing
  }

  @Override
  public void updateVisualization() {
    //myPhysics.update();
  }

  public void moveRight(){
    myPhysics.moveRight();
  }

  public void moveLeft(){
    myPhysics.moveLeft();
  }

  public void jump(){
    myPhysics.jump();
  }

}
