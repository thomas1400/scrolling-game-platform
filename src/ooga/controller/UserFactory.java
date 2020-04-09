package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import ooga.model.data.User;

public final class UserFactory {

  public static User getUser(File userFile){
    try (InputStream input = new FileInputStream(userFile)) {

      Properties prop = new Properties();

      // load a properties file
      prop.load(input);

      // get the property value and print it out
      System.out.println(prop.getProperty("db.url"));
      System.out.println(prop.getProperty("db.user"));
      System.out.println(prop.getProperty("db.password"));

    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static User createDefaultUser() {
    return null;
  }
}
