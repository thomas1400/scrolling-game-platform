package ooga.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import ooga.model.ability.Movement;
import ooga.model.behavior.Collidible;
import ooga.utility.event.CollisionEvent;


public class Entity extends ImageView implements Collidible, Manageable, Renderable {

  private static final String HARMLESS = "Harmless";
  private static final String DEFAULT_PACKAGE_CONTENT = "empty 0";
  private static final String COLLISIONS_HANDLING_PATH = "entities/collisions/";
  private static final String ADD = "add";
  private static final String SCORE = "score";
  private static final String HEALTH = "health";
  private static final String SCALE = "scale";
  private static final String LEVEL_ENDED = "levelEnd";
  private static final double INITIAL_SCORE = 0;
  private static final double DEFAULT_SCALE = 1;
  private static final double SINGLE_LIFE = 1;
  private static final double PLAYING = 0;

  private Health health;
  private Movement movement;
  private CollectiblePackage myPackage;
  private String side, top, bottom;
  private Map<String, Ability> myAbilities;
  private Map<String, Double> myInformation;
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
    myAbilities = new HashMap<>();
    myInformation = new HashMap<>();
    addHealth(new Health());
    addSideAttack(HARMLESS);
    addTopAttack(HARMLESS);
    addBottomAttack(HARMLESS);
    haveMovement = false;
    addCollectiblePackage(new CollectiblePackage(DEFAULT_PACKAGE_CONTENT));
    setScore(INITIAL_SCORE);
    size(DEFAULT_SCALE);
    levelEnd(PLAYING);
  }

  /**
   * Override any attack types as specified by the new attack type and location
   * @param location where the attack goes
   * @param attackType new type to replace old one at location
   */
  public void updateAttack(String location, String attackType) {
    try {
      Method method = Entity.class.getDeclaredMethod(ADD+location, String.class);
      method.invoke(Entity.this, attackType);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
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
    myAbilities.put("Health", health);
    health(0.0);//todo remove magic num
  }

  //used for reflection DO NOT DELETE
  public void addCollectiblePackage(Ability p){
    myPackage = (CollectiblePackage) p;
    myAbilities.put("Package", myPackage);
  }

  //used for reflection DO NOT DELETE
  private void addMovement(Ability m){
    movement = (Movement) m;
    myAbilities.put("Movement", movement);
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
  public Map<String, Ability> getAbilities() {
    return myAbilities;
  }

  @Override
  public double getData(String informationType){
    if(myInformation.containsKey(informationType))
      return myInformation.get(informationType);
    else{
      ExceptionFeedback.throwHandledException(new RuntimeException(), "You're looking for information (\""+informationType+"\" in the data map that isn't there");
      return 0;
      //todo get rid of magic val
    }
  }

  /**
   * returns if the entity has health greater than 0;
   * aka if it is dead or not
   * @return boolean tracking health status
   */
  public boolean isDead(){
    return dead;
  }

  /**
   * revives the entity if the entity dies
   */
  public void revive(){
    if(dead) {
      health.addLives(SINGLE_LIFE);
      dead = health.isDead();
    }
  }

  //todo delete when finished
  public String debug(){
    if(debuggingName.equals("Mario.png")){
     // System.out.println("Side: "+side.toString()+" Bottom: "+bottom.toString()+" top: "+top.toString());
    }
    return debuggingName;
  }

  @Override
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
      //ExceptionFeedback.throwHandledException(e, "No such method used when trying to to get the "+location+" attack");
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      ExceptionFeedback.throwHandledException(e, "No access to method used when trying to get the "+location+" attack");
      //throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      ExceptionFeedback.throwHandledException(e, "Failed to invoke method called when trying to get the "+location+" attack");
      //throw new RuntimeException(e);
    }
    return HARMLESS;
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

  /**
   * handle collisions based on a given collision event
   * find the attack the entity has at the location of the collision,
   * search in the file of the attack of the OTHER entity for the methods
   * that should be executed based on this.attackType, then execute those methods
   */
  public Entity handleCollision(CollisionEvent ce) {
    String location = ce.getCollisionLocation();
    String otherAttack = ce.getAttackType();
    String myAttack = this.getAttack(location);
    Collidible otherEntity = ce.getOther();

    if(!otherEntity.isDead()) { //fixme remove if the entities are removed later
      try {
        ResourceBundle myAttackSpecificResponseBundle = ResourceBundle
            .getBundle(COLLISIONS_HANDLING_PATH + otherAttack.toString());
        String[] methodsToCall = myAttackSpecificResponseBundle.getString(myAttack).split(" ");
        for (String s : methodsToCall) {
          if (s.equals("collect")) {
            otherEntity.otherCollectMe();
            updateMeAfterCollecting(otherEntity);
            //setScaleOfImage();
            otherEntity.otherResetAfterCollect();
          } else {
            Method method = Entity.class.getDeclaredMethod(s);
            method.invoke(Entity.this);
          }
        }
      } catch (MissingResourceException e) {
        //ExceptionFeedback.throwHandledException(e, "Couldn't find key in bundle");
        System.out.println("Couldn't find key in bundle I'm:"+ debuggingName+"; we're at: "+location);
        throw new RuntimeException(e);
      } catch (NoSuchMethodException e) {
        ExceptionFeedback
            .throwHandledException(e, "Method name was incorrect in trying to make the entity");
        //throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        ExceptionFeedback
            .throwHandledException(e, "You don't have access to that method, try again");
        //throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        ExceptionFeedback
            .throwHandledException(e, "Couldn't invoke the method when creating the entity");
        //throw new RuntimeException(e);
      }
    }
    return this;
  }

  private void updateMeAfterCollecting(Collidible other){
    this.points(other.getData(SCORE));
    this.health(other.getData(HEALTH));
    health.addLives(getData(HEALTH));
    this.size(other.getData(SCALE));
    this.levelEnd(other.getData(LEVEL_ENDED));
  }

  //used for reflection DO NOT DELETE
  private void damage(){
    health.hit();
    dead = health.isDead();
  }

  //used for reflection DO NOT DELETE
  /**
   * chooses which bounce method to use based on which speed is greater; x or y
   */
  private void bounce(){
    if(haveMovement && (Math.abs(movement.getYVelocity()) >= Math.abs(movement.getXVelocity()))){
      bounceY();
    } else if (haveMovement && Math.abs(movement.getYVelocity()) < Math.abs(movement.getXVelocity())){
      bounceX();
    }
  }

  //used for reflection DO NOT DELETE
  /**
   * note: the effects of a collectible entity will only
   * be shown within that entity in order to hide other objects
   */
  private void openPackage(){
    //todo create a bonus ability that can change score, height, etc. have that happen here as the entity is collected
    String methodToCall = myPackage.toString();
    double value = myPackage.getPackageValue();
    if(!dead && !levelEnded){
      try {
        Method method = Entity.class.getDeclaredMethod(methodToCall, Double.class);
        method.invoke(Entity.this, value);
      } catch (NoSuchMethodException e) {
        //ExceptionFeedback.throwHandledException(e, "Method name was incorrect when trying to collect the entity");
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        ExceptionFeedback.throwHandledException(e, "No access to method used when trying to collect the entity");
        //throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        ExceptionFeedback.throwHandledException(e, "Method can't be invoked when used when trying to collect the entity");
        //throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void otherCollectMe(){
    openPackage();
  }

  //used for reflection DO NOT DELETE
  private void collectMe(){
    openPackage();
    otherResetAfterCollect();
  }

  @Override
  public void otherResetAfterCollect(){
    resetScore();
    //size(DEFAULT_SCALE);
    health(0.0);
  }

  //used for reflection DO NOT DELETE
  private void points(Double value){
    score += value;
    myInformation.put(SCORE, score);
    //System.out.println("score: " + score);
  }

  //used for reflection DO NOT DELETE
  private void health(Double value){
    //health.addLives((int) Math.floor(value));
    myInformation.put(HEALTH, value);
  }

  //used for reflection DO NOT DELETE
  private void size(Double value){
    scale = value;
    //setScale();
    myInformation.put(SCALE, scale);
  }

  private void setScaleOfImage(){
    //Scale sc = new Scale(scale, scale);
    //System.out.println(this.getFitHeight());
    //System.out.println(getBoundsInParent());
    //System.out.println(this.getScaleX());
    System.out.println("scale: "+scale);
    this.setFitHeight(this.getFitHeight()*scale);
    this.setScaleX(this.getScaleX()*scale);
    this.setFitWidth(this.getFitWidth()*scale);
    this.setScaleY(this.getScaleY()*scale);
    //System.out.println(this.getFitHeight());
    //System.out.println(this.getFitWidth());
    //System.out.println(this.getFitWidth());
  }

  private void empty(Double value){
    nothing();
  }

  //used for reflection DO NOT DELETE
  private void levelEnd(Double value){
    //System.out.println("we did it");
    myInformation.put(LEVEL_ENDED, value);
    //levelEnded = value;
    success = (value!=PLAYING && this.isDead());
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
  /**
   * called every cycle, move the entity, and check for entity death
   */
  public void updateVisualization() {
    if (haveMovement) {
      movement.update(this);
    }
    dead = health.isDead();
  }

  //used for reflection DO NOT DELETE
  /**
   * return is a level is over for the specific entity
   * @return levelEnded
   */
  public boolean endedLevel(){
    return myInformation.get(LEVEL_ENDED)!=0; //convert the double to a boolean
  }

  //used for reflection DO NOT DELETE
  //fixme because this isn't in the player entity, this method will always be false for players
  // and always true for collected level end entities. ask cayla if you can delete this
  /**
   * True if a level was ended by collecting a level end entity.
   * @return success
   */
  public boolean isSuccess(){
    return success;
  }

  //used for reflection DO NOT DELETE
  //fixme where is this being used? would it be ok to delete/replace with a clear score method?
  /**
   * set the score of an entity to an incoming value
   * @param newScore new score to set the entity to
   */
  public void setScore(double newScore){
    score = newScore;
    myInformation.put(SCORE, score);
  }

  /**
   * Reset's the entity's score to the initial value
   */
  public void resetScore(){
    setScore(INITIAL_SCORE);
  }

  //used for reflection DO NOT DELETE
  /**
   * return the score of a collected entity
   * @return score
   */
  public double getScore(){
    return score;
  }

  //used for reflection DO NOT DELETE
  /**
   * Move the entity to the right if it can do so
   */
  public void moveRight(){
    setScaleX(scale);
    movement.right();
  }

  //used for reflection DO NOT DELETE
  /**
   * Move the entity to the left if it can do so
   */
  public void moveLeft(){
    setScaleX(-1*scale);
    movement.left();
  }

  //used for reflection DO NOT DELETE
  /**
   * Move the entity up if it can do so
   */
  public void jump(){
    // Old Line (changed because TINY_DISTANCE shouldn't be static)
    //setY(getY()-Physics.TINY_DISTANCE);
    setY(getY()-5);
    movement.jump();
  }

  /**
   * used for unit testing.
   * @return lives of the entity
   */
  public double debugHealth(){
    return health.getLives();
  }
}
