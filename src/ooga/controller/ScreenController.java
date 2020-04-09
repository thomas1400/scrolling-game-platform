package ooga.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.model.data.User;
import ooga.model.data.UserList;
import ooga.view.GameScreen;
import ooga.view.HomeScreen;
import ooga.view.LevelBuilderScreen;
import ooga.view.LevelSelectorScreen;
import ooga.view.Screen;
import ooga.view.SplashScreen;
import ooga.view.UserSelectorScreen;

public class ScreenController{

  private static final int INITIAL_WINDOW_WIDTH = 800;
  private static final int INITIAL_WINDOW_HEIGHT = 600;

  private Stage myStage;

  private UserList myUsers;
  private User mySelectedUser;

  private Screen myHomeScreen = new HomeScreen(this);
  //private Screen mySplashScreen = new SplashScreen();
  private Screen myUserSelectorScreen =  new UserSelectorScreen(this);
  private Screen myLevelSelectorScreen = new LevelSelectorScreen(this);
  private Screen myGameScreen;
  //private Screen myLevelBuilderScreen = new LevelBuilderScreen();

  private Map<String, Screen> myScreens = new HashMap<>();

  public ScreenController(Stage primaryStage){
    myStage = primaryStage;

    initializeScreens();

    switchToScreen("LevelSelectorScreen");
  }

  private void initializeScreens(){
    myScreens.put("HomeScreen", myHomeScreen);
    //myScreens.put("SplashScreen", mySplashScreen);
    myScreens.put("UserSelectorScreen", myUserSelectorScreen);
    myScreens.put("LevelSelectorScreen", myLevelSelectorScreen);
    //myScreens.put("LevelBuilderScreen", myLevelBuilderScreen);
  };

  public void switchToScreen(String screenName){
    Screen nextScreen = myScreens.get(screenName);
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    //File file = new File("resources/stylesheet.css");
    //nextScene.getStylesheets().add(file.toURI().toString());
    Scene lastScene = myStage.getScene();
    myStage.setScene(nextScene);
    // TODO : look at alternatives to this bugfix, added by Thomas
    if (lastScene != null) {
      lastScene.setRoot(new Pane());
    }
    myStage.show();
  };

  public void initializeNewLevel(int levelNumber){
    myGameScreen = new GameScreen(this);
    myScreens.put("GameScreen", myGameScreen);

    LevelController levelController =
        new LevelController((GameScreen)myGameScreen, mySelectedUser, levelNumber);
    ((GameScreen) myGameScreen).setLevelController(levelController);

    switchToScreen("GameScreen");
    levelController.begin();
  }

  public void setUsers(UserList users) {
    myUsers = users;
    mySelectedUser = users.getSelectedUser();
  }

  public void handleButtonPress(){

  }
}
