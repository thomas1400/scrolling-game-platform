package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.model.data.User;
import ooga.model.data.UserList;
import ooga.view.GameScreen;
import ooga.view.HomeScreen;
import ooga.view.LevelSelectorScreen;
import ooga.view.Screen;
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
    addApplicationIcon();
    initializeScreens();

    switchToScreen("HomeScreen");
  }

  private void addApplicationIcon() {
    try {
      Image icon = new Image(new FileInputStream("artwork/goomba.png"));
      myStage.getIcons().add(icon);
    } catch (Exception ignored) {
      //TODO: add actual catch
    }
  }

  private void initializeScreens(){
    myScreens.put("HomeScreen", myHomeScreen);
    //myScreens.put("SplashScreen", mySplashScreen);
    myScreens.put("UserSelectorScreen", myUserSelectorScreen);
    myScreens.put("LevelSelectorScreen", myLevelSelectorScreen);
    //myScreens.put("LevelBuilderScreen", myLevelBuilderScreen);
  };

  public void switchToScreen(String screenName){
    Scene nextScene = getScene(screenName);
    showScene(nextScene);
  }

  private void showScene(Scene nextScene) {
    Scene lastScene = myStage.getScene();
    myStage.setScene(nextScene);
    // TODO : look at alternatives to this bugfix, added by Thomas
    if (lastScene != null) {
      lastScene.setRoot(new Pane());
    }
    myStage.show();
  }

  private Scene getScene(String screenName) {
    Screen nextScreen = myScreens.get(screenName);
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    File file = new File("resources/stylesheet.css");
    nextScene.getStylesheets().add(file.toURI().toString());
    return nextScene;
  }

  ;

  public void initializeNewLevel(int levelNumber){
    myGameScreen = new GameScreen(this);
    myScreens.put("GameScreen", myGameScreen);

    LevelController levelController =
        new LevelController((GameScreen)myGameScreen, mySelectedUser, levelNumber);
    ((GameScreen) myGameScreen).setLevelController(levelController);

    Screen nextScreen = myScreens.get("GameScreen");
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    File file = new File("resources/stylesheet.css");
    nextScene.getStylesheets().add(file.toURI().toString());
    //nextScene.setOnKeyPressed(event -> levelController.handleUserInput(event));
    nextScene.setOnKeyPressed(levelController::handleUserInput);

    showScene(nextScene);

    levelController.beginLevel();
  }

  public void setUsers(UserList users) {
    myUsers = users;
    mySelectedUser = users.getSelectedUser();
  }

  public UserList getUsers() {
    return myUsers;
  }

  public void setSelectedUser(User user){
    mySelectedUser = user;
  }

  public void handleButtonPress(){

  }
}
