package ooga.controller.users;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import ooga.controller.users.User;
import ooga.exceptions.ExceptionFeedback;

public final class UserSaver {

  public static final String USERS_RESOURCE_PATH = "userdata/users";
  private static final String USER_PACKAGE = "data/userdata/";
  public static final String REGX = ",";
  public static final String LEVELS = "Levels";
  public static final String GAMES = "games";
  public static final String USER_EXTENSION = ".user";
  public static final String DEFAULT_USER = "Default User";

  /**
   * Saves a user to a file when provided a User object
   * @param user object to be saved into a user file
   */
  public static void saveUser(User user) {
    if (!user.getName().equals(DEFAULT_USER)) {
      try (OutputStream output = new FileOutputStream(
          USER_PACKAGE + user.getName() + USER_EXTENSION)) {

        Properties userProperties = new Properties();

        // set the properties value
        setSimpleProperties(user, userProperties);
        setGamesProperty(user, userProperties);
        setUnlockedLevelsProperty(user, userProperties);

        // save properties to project root folder
        userProperties.store(output, "User properties file for user: " + user.getName());

      } catch (IOException io) {
        ExceptionFeedback.throwHandledException(io, "User saving for user " + user.getName() + " "
            + "has failed. Be aware that changes from the previous level may not have been saved.");
      }
    }
  }

  private static void setGamesProperty(User user, Properties userProperties) {
    String gamesString = buildStringFromList(user.getAllGames());
    userProperties.setProperty(GAMES, gamesString);
  }

  private static void setSimpleProperties(User user, Properties userProperties) {
    ResourceBundle myUserSaverResources = ResourceBundle.getBundle(USERS_RESOURCE_PATH);

    for (String userProperty : Collections.list(myUserSaverResources.getKeys())) {
      try {
        String methodName = myUserSaverResources.getString(userProperty);
        try {
          Method m = user.getClass().getDeclaredMethod(methodName);
          userProperties.setProperty(userProperty, m.invoke(user) + "");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          ExceptionFeedback.throwBreakingException(e, "Reflection Error when accessing User "
              + "Properties during save. (Check users.properties file)");
        }
      } catch (SecurityException ignore) {
      }
    }
  }

  private static void setUnlockedLevelsProperty(User user, Properties userProperties) {
    for (String game : user.getAllGames()) {
      String unlockedLevelsString = buildStringFromList(user.getLevelsCompleted(game));
      userProperties.setProperty(game + LEVELS, unlockedLevelsString);
    }
  }

  private static String buildStringFromList(Set userPropertyList) {
    if (userPropertyList.isEmpty()) {
      return "";
    }
    StringBuilder newString = new StringBuilder();
    for (Object property : userPropertyList){
      newString.append(property.toString()).append(REGX);
    }
    return newString.toString().substring(0,newString.length()-1);
  }
}
