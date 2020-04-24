package ooga.controller.users;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import ooga.controller.UserSaver;
import ooga.model.entity.Entity;

public class User {

  private static final int POINTS_TO_LIFE_RATIO = 100;
  public static final int ADDITIONAL_LIFE = 1;
  public static final int NEGATIVE = -1;
  //FIXME: This should be loaded in from the game file
  private static final List<String> ALL_GAME_TYPES = Arrays.asList("mario",
      "flappy", "doodlejump");

  private String myName;
  private String myImageFileName;
  Map<String, Set<Integer>> myAllGameLevels;
  private int myLives;
  private IntegerProperty livesProperty;
  private int myPoints;
  private IntegerProperty pointsProperty;
  private Set<String> myGames = new HashSet<>(ALL_GAME_TYPES);

  public User(String name, String imageFileName){
    myName = name;
    myImageFileName = imageFileName;
    myAllGameLevels = new HashMap<>();
    myLives = 0;
    myPoints = 0;
    livesProperty = new SimpleIntegerProperty(myLives);
    pointsProperty = new SimpleIntegerProperty(myPoints);
  }

  public void saveUser(){
    UserSaver.saveUser(this);
  }

  public Entity asEntity(){
    //return EntityBuilder.getEntity();
    return null;
  }

  public String getName() {
    return myName;
  }

  public void setName(String name) {
    myName = name;
  }

  public Image getImage() {
    return new Image("userdata/images/" + myImageFileName);
  }

  public void setImage(String imageFileName) {
    myImageFileName = imageFileName;
  }

  public Set<Integer> getLevelsCompleted(String gameType) {
    return myAllGameLevels.get(gameType);
  }

  public void unlockNextLevel(String gameType, int currentLevel) {
    myAllGameLevels.get(gameType).add(currentLevel);
  }

  public int getLives() {
    return myLives;
  }

  public void addLife(){
    myLives += ADDITIONAL_LIFE;
    livesProperty.setValue(livesProperty.getValue() + ADDITIONAL_LIFE);
  }

  public void adjustLives(int lives) {
    myLives += lives;
    livesProperty.setValue(livesProperty.getValue() + lives);
  }

  public int getPoints() {
    return myPoints;
  }

  public void adjustPoints(int points) {
    myPoints += points;
    pointsProperty.setValue(pointsProperty.getValue() + points);
  }

  public IntegerProperty getLivesProperty() {
    return livesProperty;
  }

  public IntegerProperty getPointsProperty() {
    return pointsProperty;
  }

  public boolean canConvertPointsToLife() {
    if (myPoints > POINTS_TO_LIFE_RATIO){
      addLife();
      adjustPoints(NEGATIVE * POINTS_TO_LIFE_RATIO);
      return true;
    }
    return false;
  }

  public void setGameLevels(String gameType, Set<Integer> levelsUnlocked) {
    myAllGameLevels.put(gameType, levelsUnlocked);
  }

  public void setAllGameLevels(Map<String, Set<Integer>> allLevels){
    myAllGameLevels = allLevels;
  }

  public void setLives(int lives) {
    myLives = lives;
    livesProperty.setValue(lives);
  }

  public Set<String> getAllGames() {
    return myGames;
  }

  public void addGame(String game) {
    myGames.add(game);
  }

  //USED FOR REFLECTION, DO NOT DELETE
  public String getImageFileName(){
    return myImageFileName;
  }
}
