package ooga.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public final class UserSaver {

  public static void main(String[] args){
    createUser("Grant");
  }

  public static void createUser(String userName) {
    try (OutputStream output = new FileOutputStream("resources/users/" + userName + ".user")) {

      Properties prop = new Properties();

      // set the properties value
      prop.setProperty("db.url", "localhost 009");
      prop.setProperty("db.user", "mkyong");
      prop.setProperty("db.password", "password");

      // save properties to project root folder
      prop.store(output, "hello");

      System.out.println(prop);

    } catch (IOException io) {
      io.printStackTrace();
    }
  }
}
