package ooga.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import ooga.model.data.User;

public final class UserSaver {

  public static void saveUser(User user) {
    try (OutputStream output = new FileOutputStream("resources/users/" + user.getName() + ".user")) {

      Properties userProperties = new Properties();

      // set the properties value
      userProperties.setProperty("name", user.getName());
      userProperties.setProperty("image", user.getImageFileName());

      String unlockedLevelsString = buildStringFromList(user.getLevelsUnlocked());
      userProperties.setProperty("levelsUnlocked", unlockedLevelsString);

      userProperties.setProperty("lives", user.getLives() + "");
      userProperties.setProperty("points", user.getPoints() + "");
      userProperties.setProperty("power", user.getPower());
      userProperties.setProperty("size", user.getSize());

      // save properties to project root folder
      userProperties.store(output, "User properties file for user: " + user.getName());

    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  private static String buildStringFromList(List userPropertyList) {
    StringBuilder newString = new StringBuilder();
    for (Object property : userPropertyList){
      newString.append(property.toString()).append(",");
    }
    return newString.toString().substring(0,newString.length()-1);
  }
}
