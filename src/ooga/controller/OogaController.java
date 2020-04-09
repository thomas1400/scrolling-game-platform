package ooga.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import ooga.model.data.User;

public class OogaController {

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "resources/users";

  private List<User> myUsers = new ArrayList<>();

  private ScreenController myScreenController;

  public OogaController(Stage primaryStage){
    loadUsers();

    ScreenController myScreenController = new ScreenController(primaryStage);
    myScreenController.setUsers(myUsers);
  }

  private void loadUsers() {
    FilenameFilter filter = (f, name) -> name.endsWith(USER_FILE_EXTENSION);

    File folder = new File(USERS_PATH_NAME);
    File[] listOfFiles = folder.listFiles(filter);

    if (listOfFiles != null) {
      for (File userFile : listOfFiles) {
        //FIXME: Remove print statement
        System.out.println("Loading User in File: " + userFile.getName());
        myUsers.add(UserFactory.getUser(userFile));
      }
    } else {
      myUsers.add(UserFactory.createDefaultUser());
    }
  }

  private ScreenController createScreenController(){return null;}
}
