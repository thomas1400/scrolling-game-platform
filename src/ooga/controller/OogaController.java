package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import javafx.stage.Stage;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.User;
import ooga.controller.users.UserList;

public class OogaController {

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "resources/users";
  private static final String LEVEL_FILE_EXTENSION = ".level";
  public static final String LEVEL_PATH_NAME = "resources/levels";

  private UserList myUsers = new UserList();
  private BasicLevelList myLevels = new BasicLevelList();

  public OogaController(Stage primaryStage) throws FileNotFoundException {
    loadUsers();
    loadLevels();

    //handleCloseEvent(primaryStage);

    new ScreenController(primaryStage, myUsers, myLevels);
  }

  private void handleCloseEvent(Stage primaryStage) {
    primaryStage.setOnCloseRequest(event -> {
      for (User user : myUsers){
        UserSaver.saveUser(user);
      }
      System.out.println("Users Saved Successfully");
    });
  }

  private void loadUsers() {
    File[] listOfFiles = getFilteredListOfFiles(USER_FILE_EXTENSION, USERS_PATH_NAME);

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

  private void loadLevels() throws FileNotFoundException {
    File[] listOfFiles = getFilteredListOfFiles(LEVEL_FILE_EXTENSION, LEVEL_PATH_NAME);

    if (listOfFiles != null) {
      for (File levelFile : listOfFiles) {
        //FIXME: Remove print statement
        System.out.println("Loading Level from File: " + levelFile.getName());
        myLevels.addBasicLevel(LevelBuilder.buildBasicLevel(levelFile));
      }
    }
  }

  private File[] getFilteredListOfFiles(String levelFileExtension, String levelPathName) {
    FilenameFilter filter = (f, name) -> name.endsWith(levelFileExtension);

    File folder = new File(levelPathName);
    return folder.listFiles(filter);
  }
}
