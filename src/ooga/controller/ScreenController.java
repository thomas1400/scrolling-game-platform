package ooga.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import ooga.model.data.User;
import ooga.view.GameScreen;
import ooga.view.HomeScreen;
import ooga.view.LevelBuilderScreen;
import ooga.view.LevelSelectorScreen;
import ooga.view.Screen;
import ooga.view.SplashScreen;
import ooga.view.UserSelectorScreen;

public class ScreenController{

  private Pane myMainPane;
  private User myUser;

  private Screen myHomeScreen = new HomeScreen(this);
  private Screen mySplashScreen = new SplashScreen();
  private Screen myUserSelectorScreen =  new UserSelectorScreen(this);
  private Screen myLevelSelectorScreen = new LevelSelectorScreen(this);
  private Screen myGameScreen = new GameScreen(this);
  private Screen myLevelBuilderScreen = new LevelBuilderScreen();

  private Map<String, Screen> myScreens = new HashMap<>();

  public ScreenController(Pane mainPane){
    myMainPane = mainPane;
    myScreens.put("game", myGameScreen);
  }

  private void initializeScreens(){};

  public void handleButtonPress(){

  }

  public void switchToScreen(String screenName){
    Screen nextScreen = myScreens.get(screenName);
    myMainPane.getChildren().clear();
    myMainPane.getChildren().add(nextScreen);
    //TODO: check if there needs to be additional actions, for instance initializing the game
    // **or simply use the initialize new level as public?
  };

  private void initializeNewLevel(String levelFile){
    myGameScreen = new GameScreen(this);
    LevelController levelController =
        new LevelController((GameScreen)myGameScreen, myUser, levelFile);
    ((GameScreen) myGameScreen).setLevelController(levelController);
    levelController.begin();
  }

  public void setUsers(List<User> myUsers) {

  }
}
