package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import ooga.controller.data.User;

public final class UserFactory {

  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();
      prop.load(input);

      Set<Integer> levelsUnlocked = getUnlockedLevelsSet(prop);

      User createdUser = createBaseUser(prop);
      addAdditionalProperties(prop, createdUser, levelsUnlocked);

      return createdUser;

    } catch (IOException ex) {
      //FIXME. LET'S NOT FAIL THE CLASS :))
      ex.printStackTrace();
      return null;
    }
  }

  private static void addAdditionalProperties(Properties prop, User createdUser,
      Set<Integer> levelsUnlocked) {
    createdUser.setPower(prop.getProperty("power"));
    createdUser.setSize(prop.getProperty("size"));
    createdUser.adjustPoints(Integer.parseInt(prop.getProperty("points")));
    createdUser.setLevelsUnlocked(levelsUnlocked);
    createdUser.setLives(Integer.parseInt(prop.getProperty("lives")));
  }

  private static User createBaseUser(Properties prop) {
    return new User(prop.getProperty("name"), prop.getProperty("image"));
  }

  private static Set<Integer> getUnlockedLevelsSet(Properties prop) {
    Set<Integer> levelsUnlocked = new HashSet<>();
    if (prop.getProperty("levelsUnlocked").equals("")) {
      return new HashSet<>();
    }
    for (String unlockedLevel : prop.getProperty("levelsUnlocked").split(",")){
      levelsUnlocked.add(Integer.parseInt(unlockedLevel));
    }
    return levelsUnlocked;
  }

  public User makeUser(){
    return null;
  }


  public static User getDefaultUser() {
    //TODO: eventually just have this call getUser(new File("Default.user"));
    HashSet<Integer> defaultLevelsUnlocked = new HashSet<>();
    defaultLevelsUnlocked.add(0);

    User defaultUser = new User("Default User", "Mario.png");
    defaultUser.setLevelsUnlocked(defaultLevelsUnlocked);
    defaultUser.setLives(3);

    return defaultUser;
  }
}
