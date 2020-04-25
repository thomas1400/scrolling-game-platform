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
import ooga.exceptions.ExceptionFeedback;

public final class UserFactory {

  public static final String DEFAULT_USER_FILEPATH = "data/userdata/default/DefaultUser.user";
  public static final String DEFAULT_COMPLETED_LEVELS = "0";
  public static final String REGEX = ",";

  public static final String LIVES = "lives";
  public static final String POINTS = "points";
  public static final String NAME = "name";
  public static final String IMAGE = "image";
  public static final String GAMES = "games";
  public static final String LEVELS = "Levels";

  /**
   * Builds a User based off of a user file
   * @param userFile file from which the user properties are read
   * @return the newly created User
   */
  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();
      prop.load(input);

      Map<String, Set<Integer>> allGameLevels = getGameLevelsMap(prop);

      return createBaseUser(prop, allGameLevels);

    } catch (IOException ex) {
      ExceptionFeedback.throwHandledException(ex, "User File " + userFile + " does not exist.");
      return null;
    }
  }

  private static Map<String, Set<Integer>> getGameLevelsMap(Properties prop) {
    Map<String, Set<Integer>> allGameLevels = new HashMap<>();
    for (String game : prop.getProperty(GAMES).split(REGEX)){
      allGameLevels.put(game, getUnlockedLevelsSet(game, prop));
    }
    return allGameLevels;
  }

  private static Set<Integer> getUnlockedLevelsSet(String gameType, Properties prop) {
    Set<Integer> levelsUnlocked = new HashSet<>();
    String[] levels = prop.getProperty(gameType + LEVELS, DEFAULT_COMPLETED_LEVELS).split(REGEX);
    for (String unlockedLevel : levels){
      levelsUnlocked.add(Integer.parseInt(unlockedLevel));
    }
    return levelsUnlocked;
  }

  private static User createBaseUser(Properties prop,
      Map<String, Set<Integer>> allGameLevels) {
    User baseUser = new User(prop.getProperty(NAME), prop.getProperty(IMAGE));
    addAdditionalProperties(prop, baseUser, allGameLevels);
    return baseUser;
  }

  private static void addAdditionalProperties(Properties prop, User createdUser,
      Map<String, Set<Integer>> allGameLevels) {
    createdUser.adjustPoints(Integer.parseInt(prop.getProperty(POINTS)));
    createdUser.setAllGameLevels(allGameLevels);
    createdUser.setLives(Integer.parseInt(prop.getProperty(LIVES)));
  }

  /**
   * @return a default user created from the DefaultUser properties file
   */
  public static User getDefaultUser() {
    File defaultUserFile = new File(DEFAULT_USER_FILEPATH);
    return getUser(defaultUserFile);
  }
}
