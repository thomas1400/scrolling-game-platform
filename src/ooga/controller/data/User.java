package ooga.controller.data;

import java.util.List;
import javafx.scene.image.Image;
import ooga.controller.UserSaver;
import ooga.model.entity.Entity;

public class User {

  private static final int POINTS_TO_LIFE_RATIO = 100;
  private String myName;
  private String myImageFileName;
  private List<Integer> myLevelsUnlocked;
  private int myLives;
  private int myPoints = 0;
  private String myPower = "none";
  private String mySize = "small";
  //private List<String> myAchievements;

  public User(String name, String imageFileName, List<Integer> levelsUnlocked, int lives){
    myName = name;
    myImageFileName = imageFileName;
    myLevelsUnlocked = levelsUnlocked;
    myLives = lives;
  }

  public void saveUser(){
    UserSaver.saveUser(this);
  };

  public void saveUserToFile(String fileName){
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
    return new Image(myImageFileName);
  }

  public String getImageFileName() {
    return myImageFileName;
  }

  public void setImage(String imageFileName) {
    myImageFileName = imageFileName;
  }

  public List<Integer> getLevelsUnlocked() {
    return myLevelsUnlocked;
  }

  public void addUnlockedLevel(int unlockedLevel) {
    myLevelsUnlocked.add(unlockedLevel);
  }

  public int getLives() {
    return myLives;
  }

  public void addLife(){
    myLives += 1;
  }

  public void adjustLives(int lives) {
    myLives += lives;
  }

  public int getPoints() {
    return myPoints;
  }

  public void addPoint(){
    myPoints += 1;
  }

  public void adjustPoints(int points) {
    myPoints += points;
  }

  public String getPower() {
    return myPower;
  }

  public void setPower(String power) {
    myPower = power;
  }

  public String getSize() {
    return mySize;
  }

  public void setSize(String size) {
    mySize = size;
  }

  public boolean checkPointsToLife() {
    if (myPoints > POINTS_TO_LIFE_RATIO){
      addLife();
      adjustPoints(-1 * POINTS_TO_LIFE_RATIO);
      return true;
    }
    return false;
  }
}
