package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import ooga.controller.users.User;

public final class UserFactory {

  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();
      prop.load(input);

      Map<String, Set<Integer>> allGameLevels = new HashMap<>();
      for (String game : prop.getProperty("games").split(",")){
        allGameLevels.put(game, getUnlockedLevelsSet(game, prop));
      }

      User createdUser = createBaseUser(prop);
      addAdditionalProperties(prop, createdUser, allGameLevels);

      return createdUser;

    } catch (IOException ex) {
      //FIXME. LET'S NOT FAIL THE CLASS :))
      ex.printStackTrace();
      return null;
    }
  }

  private static void addAdditionalProperties(Properties prop, User createdUser,
      Map<String, Set<Integer>> allGameLevels) {
    createdUser.setPower(prop.getProperty("power"));
    createdUser.setSize(prop.getProperty("size"));
    createdUser.adjustPoints(Integer.parseInt(prop.getProperty("points")));
    createdUser.setAllGameLevels(allGameLevels);
    createdUser.setLives(Integer.parseInt(prop.getProperty("lives")));
  }

  private static User createBaseUser(Properties prop) {
    return new User(prop.getProperty("name"), prop.getProperty("image"));
  }

  private static Set<Integer> getUnlockedLevelsSet(String gameType, Properties prop) {
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
    User defaultUser = new User("Default User", "Mario.png");
    defaultUser.setGameLevels("mario", getSetWithLevelZero());
    defaultUser.setGameLevels("flappy", getSetWithLevelZero());
    defaultUser.setGameLevels("doodle", getSetWithLevelZero());
    defaultUser.setLives(3);

    return defaultUser;
  }

  private static HashSet<Integer> getSetWithLevelZero() {
    HashSet<Integer> defaultLevelsUnlocked = new HashSet<>();
    defaultLevelsUnlocked.add(0);
    return defaultLevelsUnlocked;
  }
}
