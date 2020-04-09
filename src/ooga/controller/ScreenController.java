package ooga.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.model.data.User;
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
  private User myUser;

  private Screen myHomeScreen = new HomeScreen(this);
  //private Screen mySplashScreen = new SplashScreen();
  //private Screen myUserSelectorScreen =  new UserSelectorScreen(this);
  private Screen myLevelSelectorScreen = new LevelSelectorScreen(this);
  private Screen myGameScreen;
  //private Screen myLevelBuilderScreen = new LevelBuilderScreen();

  private Map<String, Screen> myScreens = new HashMap<>();

  public ScreenController(Stage primaryStage){
    myStage = primaryStage;

    initializeScreens();

    switchToScreen("levelSelector");
  }

  private void initializeScreens(){
    myScreens.put("home", myHomeScreen);
    //myScreens.put("splash", mySplashScreen);
    //myScreens.put("userSelector", myUserSelectorScreen);
    myScreens.put("levelSelector", myLevelSelectorScreen);
    //myScreens.put("levelBuilder", myLevelBuilderScreen);
  };

  public void switchToScreen(String screenName){
    Screen nextScreen = myScreens.get(screenName);
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    //File file = new File("resources/stylesheet.css");
    //nextScene.getStylesheets().add(file.toURI().toString());
    myStage.setScene(nextScene);
    myStage.show();
  };

  private void initializeNewLevel(String levelFile){
    myGameScreen = new GameScreen(this);
    myScreens.put("game", myGameScreen);

    LevelController levelController =
        new LevelController((GameScreen)myGameScreen, myUser, levelFile);
    ((GameScreen) myGameScreen).setLevelController(levelController);

    switchToScreen("game");
    levelController.begin();
  }

  public void setUsers(List<User> myUsers) {

  }

  public void handleButtonPress(){

  }
}
