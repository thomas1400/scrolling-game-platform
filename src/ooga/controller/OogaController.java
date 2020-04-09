package ooga.controller;

import java.io.File;
import java.io.FilenameFilter;
import javafx.stage.Stage;
import ooga.model.data.UserList;

public class OogaController {

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "resources/users";

  private UserList myUsers = new UserList();

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
        myUsers.addUser(UserFactory.getUser(userFile));
      }
    } else {
      myUsers.addUser(UserFactory.getDefaultUser());
    }
  }

  private ScreenController createScreenController(){return null;}
}
