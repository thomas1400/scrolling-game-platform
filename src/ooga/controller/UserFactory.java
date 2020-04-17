package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import ooga.controller.data.User;

public final class UserFactory {

  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();
      prop.load(input);

      List<Integer> levelsUnlocked = getUnlockedLevelsArray(prop);

      User createdUser = createBaseUser(prop, levelsUnlocked);

      addAdditionalProperties(prop, createdUser);

      return createdUser;

    } catch (IOException ex) {
      //FIXME. LET'S NOT FAIL THE CLASS :))
      ex.printStackTrace();
      return null;
    }
  }

  private static void addAdditionalProperties(Properties prop, User createdUser) {
    createdUser.setPower(prop.getProperty("power"));
    createdUser.setSize(prop.getProperty("size"));
    createdUser.adjustPoints(Integer.parseInt(prop.getProperty("points")));
  }

  private static User createBaseUser(Properties prop, List<Integer> levelsUnlocked) {
    return new User(
            prop.getProperty("name"),
            prop.getProperty("image"),
            levelsUnlocked,
            Integer.parseInt(prop.getProperty("lives"))
        );
  }

  private static List<Integer> getUnlockedLevelsArray(Properties prop) {
    ArrayList<Integer> levelsUnlocked = new ArrayList<>();
    if (prop.getProperty("levelsUnlocked").equals("")) {
      return new ArrayList<>();
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
    List<Integer> defaultLevelsUnlocked = new ArrayList<>();
    defaultLevelsUnlocked.add(0);
    return new User("Default User", "Mario.png", defaultLevelsUnlocked, 3);
  }
}
