package ooga.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.ability.Ability;
import ooga.model.ability.CollectiblePackage;
import ooga.model.ability.Health;
import ooga.model.behavior.Collidible;
import ooga.model.ability.Physics;
import ooga.utility.event.CollisionEvent;

public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private static final String HARMLESS = "Harmless";
  private static final String METHOD_HOSTS = "entitymethods/MethodHosts";
  private static final String DEFAULT_PACKAGE_CONTENT = "empty 0";
  private static final String ADD = "add";
  private static final String SCORE = "score";
  private static final String HEALTH = "health";
  private static final String PHYSICS = "physics";
  private static final String SCALE = "scale";
  private static final String SIDE = "side";
  private static final String TOP = "top";
  private static final String BOTTOM = "bottom";
  private static final String LEVEL_ENDED = "levelEnd";
  private static final double INITIAL_SCORE = 0;
  private static final double DEFAULT_SCALE = 1;
  private static final double SINGLE_LIFE = 1;
  private static final double DEAD = 0;
  private static final double PLAYING = 0;
  private static final double TINY_DISTANCE = 5;

  private String myGameType;
  private Health health;
  private Physics physics;
  private CollectiblePackage myPackage;
  private Map<String, String> myAttacks;
  private Map<String, Double> myInformation;
  private boolean haveMovement, levelEnded;

  /**
   * Create default health and attacks, which can be overwritten later
   * using the addAbility method
   */
  public Entity(Image image, String name, String gameType){
    super(image);
    myGameType = gameType;
    myAttacks = new HashMap<>();
    myInformation = new HashMap<>();
    addHealth(new Health());
    updateAttack(SIDE, HARMLESS);
    updateAttack(TOP, HARMLESS);
    updateAttack(BOTTOM, HARMLESS);
    haveMovement = false;
    addCollectiblePackage(new CollectiblePackage(DEFAULT_PACKAGE_CONTENT));
    resetScore();
    setInfo(SCALE, DEFAULT_SCALE);
    levelEnd(PLAYING);
  }

  private void setInfo(String type, double value){
    myInformation.put(type.toLowerCase(), value);
  }

  /**
   * Override any attack types as specified by the new attack type and location
   * @param location where the attack goes
   * @param attackType new type to replace old one at location
   */
  public void updateAttack(String location, String attackType) {
    myAttacks.put(location.toLowerCase(), attackType);
  }

  @Override
  /**
   * Get the attack of the specific side
   * @param location
   * @return call method to get the attack of the specific location
   */
  public String getAttack(String location){
    if(myAttacks.containsKey(location.toLowerCase())){
      return myAttacks.get(location.toLowerCase());
    } else {
      ExceptionFeedback.throwBreakingException(new NullPointerException(),
          "We're looking for the "+location.toLowerCase()+" attack, but this entity doesn't seem to have one. "
              + "Check that you're looking for either \"side\", \"top\", or \"bottom\"");
    }
    return HARMLESS;
  }

  /**
   * Add abilities to the entity. This is a general method that allows all
   * ability objects to be added to the entity
   * @param abilityType what ability to add, directs it to the correct specific method
   * @param ability the ability object to be handed to this entity
   */
  public void addAbility(String abilityType, Ability ability){
    try {
      Method method = Entity.class.getDeclaredMethod(ADD+abilityType, Ability.class);
      method.invoke(Entity.this, ability);
    } catch (NoSuchMethodException e) {
      ExceptionFeedback.throwHandledException(e, "No method to create the "+abilityType+" ability for the entity");
    } catch (IllegalAccessException e) {
      ExceptionFeedback.throwHandledException(e, "Can't make that "+abilityType+"ability");
    } catch (InvocationTargetException e) {
      ExceptionFeedback.throwHandledException(e, "Can't invoke method to make "+abilityType);
    }
  }

  private void addHealth(Ability h){
    health = (Health) h;
    health(DEAD);
    setInfo(HEALTH, DEAD); //THIS IS INTENTIONAL. THIS STORES THE DELTA HEALTH
  }

  public void addCollectiblePackage(Ability p){
    myPackage = (CollectiblePackage) p;
  }

  //used for reflection DO NOT DELETE
  private void addPhysics(Ability p){
    physics = (Physics) p;
    haveMovement = true;
  }

  @Override
  public double getData(String informationType){
    if(myInformation.containsKey(informationType))
      return myInformation.get(informationType);
    else{
      ExceptionFeedback.throwHandledException(new NullPointerException(), "You're looking for information (\""+informationType+"\" in the data map that isn't there");
      return PLAYING;
    }
  }

  /**
   * returns if the entity has health greater than 0;
   * aka if it is dead or not
   * @return boolean tracking health status
   */
  public boolean isDead(){
    return health.isDead();
  }

  /**
   * revives the entity if the entity dies
   */
  public void revive(){
    if(health.isDead()) {
      health.addLives(SINGLE_LIFE);
    }
  }

  /**
   * handle collisions based on a given collision event
   * find the attack the entity has at the location of the collision,
   * search in the file of the attack of the OTHER entity for the methods
   * that should be executed based on this.attackType, then execute those methods
   */
  public Entity handleCollision(CollisionEvent ce) {
    String myAttack = this.getAttack(ce.getCollisionLocation());
    Collidible otherEntity = ce.getOther();
    try {
      createAndInvokeResponse(ce, myAttack, otherEntity);
    } catch (MissingResourceException e) {
      ExceptionFeedback.throwBreakingException(e,"Couldn't find key"+myAttack+" in "+ce.getAttackType()+".properties");
    } catch (NoSuchMethodException e) {
      ExceptionFeedback
          .throwHandledException(e, "Method name was incorrect in collision behavior, check "+ce.getAttackType()+".properties");
    } catch (IllegalAccessException e) {
      ExceptionFeedback.throwHandledException(e, "You don't have access to that method");
    } catch (InvocationTargetException e) {
      ExceptionFeedback.throwHandledException(e, "Couldn't invoke method; handling the collision");
    }
    return this;
  }

  private void createAndInvokeResponse(CollisionEvent ce, String myAttack, Collidible otherEntity)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    String gameSpecificFilePath = "gamedata/"+myGameType+"/entities/";
    ResourceBundle response = ResourceBundle.getBundle(gameSpecificFilePath + "collisions/" + ce.getAttackType());
    String[] methodsToCall = response.getString(myAttack).split(" ");
    for (String s : methodsToCall) {
      checkForHostAndCall(otherEntity, s);
    }
  }

  private void checkForHostAndCall(Collidible otherEntity, String methodName)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    ResourceBundle methodHolder = ResourceBundle.getBundle(METHOD_HOSTS); //this holds list of allowed methods
    for(String key : Collections.list(methodHolder.getKeys())) {
      if (key.contains(methodName) && methodHolder.getString(key).equalsIgnoreCase(PHYSICS)) {
        callPhysics(methodName);
        return;
      }
    }
    Method method = Entity.class.getDeclaredMethod(methodName, Collidible.class);
    method.invoke(Entity.this, otherEntity);
  }

  private void callPhysics(String methodName){
    if(haveMovement) {
      physics.reflectMethod(methodName);
    }
  }


  private void updateMeAfterCollecting(Collidible other){
    this.setInfo(SCORE, other.getData(SCORE));
    this.setInfo(HEALTH, other.getData(HEALTH));
    health.addLives(getData(HEALTH));
    this.setInfo(SCALE, other.getData(SCALE));
    this.levelEnd(other.getData(LEVEL_ENDED));
  }

  //used for reflection DO NOT DELETE
  private void damage(Collidible otherEntity){
    health.damage();
  }

  @Override
  /**
   * Collect the package, buy calling the method specified in the package
   * note: the effects of a collectible entity will only
   * be shown within that entity in order to hide other objects
   */
  public void otherCollectMe(){
    String methodToCall = myPackage.toString();
    double value = myPackage.getPackageValue();
    if(!health.isDead() && !levelEnded){
      try {
        Method method = Entity.class.getDeclaredMethod(methodToCall, Double.class);
        method.invoke(Entity.this, value);
      } catch (NoSuchMethodException e) {
        ExceptionFeedback.throwBreakingException(e, "Method "+methodToCall+" was incorrect when trying to collect the entity");
      } catch (IllegalAccessException e) {
        ExceptionFeedback.throwBreakingException(e, "No access to method"+methodToCall+" used when trying to collect the entity");
      } catch (InvocationTargetException e) {
        ExceptionFeedback.throwBreakingException(e, "Method "+methodToCall+"can't be invoked when used when trying to collect the entity");
      }
    }
  }

  @Override
  /**
   * Reset the stats back to the defaults, but dead
   * assumes will only be called after an entity is collected
   */
  public void otherResetAfterCollect(){
    resetScore();
    health(DEAD);
  }

  //used for reflection DO NOT DELETE. assumes it will be called only when collecting objects
  private void points(Double value){
    myInformation.put(SCORE, value);
  }

  private void health(Double value){
    myInformation.put(HEALTH, value);
  }

  @Override
  public void size(Double value){
    myInformation.put(SCALE, value);
  }

  private void levelEnd(Double value){
    myInformation.put(LEVEL_ENDED, value);
    levelEnded = (value!=PLAYING);
  }

  //used for internal reflection DO NOT DELETE
  private void collect(Collidible otherEntity){
    otherEntity.size(myInformation.get(SCALE)); //stops the overwriting of scale back to 1 when reading from the other guy
    otherEntity.otherCollectMe();
    updateMeAfterCollecting(otherEntity);
    otherEntity.otherResetAfterCollect();
  }

  //used for reflection DO NOT DELETE
  private void nothing(Collidible collidible){
    //do nothing, intentionally empty
  }

  //used for reflection DO NOT DELETE
  private void empty(Double value){
    //intentionally empty
  }

  @Override
  /**
   * called every cycle, move the entity, and check for entity death
   */
  public void updateVisualization() {
    if (haveMovement) {
      physics.update(this);
    }
    levelEnd(health.isDead()?1.0:0.0); //convert boolean to a double
  }

  /**
   * return is a level is over for the specific entity
   * @return levelEnded
   */
  public boolean endedLevel(){
    return getData(LEVEL_ENDED)!=0;
  }

  /**
   * Reset's the entity's score to the initial value
   */
  public void resetScore(){
    myInformation.put(SCORE, INITIAL_SCORE);
  }

  /**
   * return the score of a collected entity
   * @return score
   */
  public double getScore(){
    //there will always be something in this map, as it's put there in the constructor
    return myInformation.get(SCORE);
  }

  /** used for reflection DO NOT DELETE
   * Move the entity to the right if it can do so
   */
  public void moveRight(){
    setScaleX(myInformation.get(SCALE));
    physics.moveRight();
  }

  /** used for reflection DO NOT DELETE
   * Move the entity to the left if it can do so
   */
  public void moveLeft(){
    setScaleX(-1*myInformation.get(SCALE));
    physics.moveLeft();
  }

  /** used with reflection DO NOT DELETE
   * Move the entity up if it can do so
   */
  public void jump(){
    setY(getY()-TINY_DISTANCE);
    physics.jump();
  }

  /**
   * @return lives of the entity
   */
  public double getLives(){
    return health.getLives();
  }

  /**
   * set the number of lives to a new value
   * @param newNumLives
   */
  public void setLives(double newNumLives){
    health.setLives(newNumLives);
  }
}