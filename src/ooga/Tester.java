package ooga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import ooga.controller.LevelBuilder;
import ooga.controller.LevelController;
import ooga.controller.UserFactory;
import ooga.controller.UserSaver;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.model.data.User;
import ooga.view.GameScreen;

public class Tester {

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println(UserFactory.getUser(new File("resources/users/Cayla.user")));
  }
}
