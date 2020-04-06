package ooga;

import java.io.File;
import java.io.FilenameFilter;
import ooga.controller.LevelController;
import ooga.model.data.Level;
import ooga.view.GameScreen;

public class Tester {

  public static void main(String[] args){
    FilenameFilter filter = (f, name) -> name.endsWith(".user");

    File folder = new File("resources/users");
    File[] listOfFiles = folder.listFiles(filter);

    assert listOfFiles != null;
    for (File userFile : listOfFiles) {
      System.out.println("Loading User in File: " + userFile.getName());
      //loadUser(userFile);
    }
  }
}
