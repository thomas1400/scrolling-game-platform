package ooga.controller.users;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import ooga.controller.UserSaver;

public class User {

  private static final int POINTS_TO_LIFE_RATIO = 100;
  public static final int ADDITIONAL_LIFE = 1;
  public static final int NEGATIVE = -1;
  public static final String USERDATA_IMAGES = "userdata/images/";
  public static final String GAMES = "games";
  public static final ResourceBundle GAME_TYPES_RESOURCES = ResourceBundle.getBundle(GAMES);
  private static final Set<String> ALL_GAME_TYPES = GAME_TYPES_RESOURCES.keySet();

  private String myName;
  private String myImageFileName;
  private Map<String, Set<Integer>> myAllGameLevels = new HashMap<>();;
  private int myLives;
  private IntegerProperty livesProperty;
  private int myPoints;
  private IntegerProperty pointsProperty;
  private Set<String> myGames = new HashSet<>(ALL_GAME_TYPES);

  public User(String name, String imageFileName){
    myName = name;
    myImageFileName = imageFileName;
    livesProperty = new SimpleIntegerProperty(myLives);
    pointsProperty = new SimpleIntegerProperty(myPoints);
  }

  public void saveUser(){
    UserSaver.saveUser(this);
  }

  public String getName() {
    return myName;
  }

  public void setName(String name) {
    myName = name;
  }

  public Image getImage() {
    return new Image(USERDATA_IMAGES + myImageFileName);
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

  //USED FOR REFLECTION, DO NOT DELETE
  public String getImageFileName(){
    return myImageFileName;
  }
}
