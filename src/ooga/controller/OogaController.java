package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;

public class OogaController {

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "data/userdata";
  private static final String GAME_TYPE = "mario";

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
      //CAN ADD CLOSE EVENTS HERE
    });
  }

  private void loadUsers() {
    File[] listOfFiles = getFilteredListOfFiles(USER_FILE_EXTENSION, USERS_PATH_NAME);

    if (listOfFiles != null) {
      for (File userFile : listOfFiles) {
        myUsers.addUser(UserFactory.getUser(userFile));
      }
    } else {
      myUsers.addUser(UserFactory.getDefaultUser());
    }
  }

  private void loadLevels() throws FileNotFoundException {
    ResourceBundle myLevelsBundle = ResourceBundle.getBundle(
        "gamedata/"+GAME_TYPE+"/levels/resources/levelOrder");

    String[] levelNumbers = myLevelsBundle.getString("levelNumbers").split(",");

    for (String levelNumberString : levelNumbers) {
      int levelNumber = Integer.parseInt(levelNumberString);
      File levelFile =
          new File("data/gamedata/"+GAME_TYPE+"/levels/" + myLevelsBundle.getString(levelNumberString) +
          ".level");
      myLevels.addBasicLevel(LevelBuilder.buildBasicLevel(levelNumber, levelFile));
    }
  }

  private File[] getFilteredListOfFiles(String levelFileExtension, String levelPathName) {
    FilenameFilter filter = (f, name) -> name.endsWith(levelFileExtension);

    File folder = new File(levelPathName);
    return folder.listFiles(filter);
  }
}
