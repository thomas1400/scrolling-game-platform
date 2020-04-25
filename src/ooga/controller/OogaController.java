package ooga.controller;

import java.io.File;
import java.io.FilenameFilter;
import javafx.stage.Stage;
import ooga.controller.users.UserList;

public class OogaController {

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "data/userdata";

  private UserList myUsers = new UserList();

  /**
   * Constructs an Ooga Controller which is responsible for handling in the detection of and
   * creation of users. Ooga Controller then passes this UserList to a screen controller which
   * oversees the visual aspects of the application.
   * @param primaryStage the application stage where ScreenController will display scenes.
   */
  public OogaController(Stage primaryStage) {
    loadUsers();
    //handleCloseEvent(primaryStage);
    new ScreenController(primaryStage, myUsers);
  }

  private void handleCloseEvent(Stage primaryStage) {
    primaryStage.setOnCloseRequest(event -> {
      //CAN ADD CLOSE EVENTS HERE
    });
  }

  private void loadUsers() {
    File[] listOfFiles = getFilteredListOfFiles();

    if (listOfFiles != null) {
      for (File userFile : listOfFiles) {
        myUsers.addUser(UserFactory.getUser(userFile));
      }
    } else {
      myUsers.addUser(UserFactory.getDefaultUser());
    }
  }

  private File[] getFilteredListOfFiles() {
    FilenameFilter filter = (f, name) -> name.endsWith(USER_FILE_EXTENSION);

    File folder = new File(USERS_PATH_NAME);
    return folder.listFiles(filter);
  }
}
