package ooga.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ooga.model.data.User;
import ooga.view.MainDisplayPane;

public class OogaController {

  private static final int INITIAL_WINDOW_WIDTH = 720;
  private static final int INITIAL_WINDOW_HEIGHT = 540;

  private static final String USER_FILE_EXTENSION = ".user";
  public static final String USERS_PATH_NAME = "resources/users";

  private List<User> myUsers = new ArrayList<>();

  private ScreenController myScreenController;

  public OogaController(Stage primaryStage){
    loadUsers();
    
    MainDisplayPane mainPane = createAppPane();

    ScreenController myScreenController = new ScreenController(mainPane);
    myScreenController.setUsers(myUsers);

    Scene myHomeScene = new Scene(mainPane, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    myHomeScene.getStylesheets().add("stylesheet.css");

    primaryStage.setScene(myHomeScene);
    primaryStage.show();
  }

  private MainDisplayPane createAppPane() {
    MainDisplayPane appPane = new MainDisplayPane();
    appPane.setPadding(new Insets(15, 15, 15, 15));
    return appPane;
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
      createDefaultUser();
    }
  }

  private void createDefaultUser() {
  }

  private ScreenController createScreenController(){return null;}
}
