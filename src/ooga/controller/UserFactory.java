package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import ooga.model.data.User;

public final class UserFactory {

  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();
      prop.load(input);

      ArrayList<Integer> levelsUnlocked = new ArrayList<>();
      for (String unlockedLevel : prop.getProperty("levelsUnlocked").split(",")){
        levelsUnlocked.add(Integer.parseInt(unlockedLevel));
      }

      User createdUser = new User(
          prop.getProperty("name"),
          prop.getProperty("image"),
          levelsUnlocked,
          Integer.parseInt(prop.getProperty("lives"))
      );


      return createdUser;

    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static User getDefaultUser() {
    //TODO: eventually just have this call getUser(new File("Default.user"));
    List<Integer> defaultLevelsUnlocked = new ArrayList<>();
    defaultLevelsUnlocked.add(1);
    return new User("Default User", "Mario.png", defaultLevelsUnlocked, 3);
  }
}
