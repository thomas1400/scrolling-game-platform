package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.controller.data.BasicLevel;
import ooga.controller.data.BasicLevelList;
import ooga.controller.data.User;
import ooga.controller.data.UserList;
import ooga.view.screen.GameScreen;
import ooga.view.screen.HomeScreen;
import ooga.view.screen.LevelSelectorScreen;
import ooga.view.screen.Screen;
import ooga.view.screen.UserSelectorScreen;

public class ScreenController{

  private static final int INITIAL_WINDOW_WIDTH = 800;
  private static final int INITIAL_WINDOW_HEIGHT = 600;
  public static final String ARTWORK_GOOMBA_PNG = "artwork/goomba.png";

  private Stage myStage;
  private UserList myUsers;
  private User mySelectedUser;
  private BasicLevelList myBasicLevels;

  private Map<String, Screen> myScreens = new HashMap<>();

  private GameScreen myGameScreen;
  private LevelController myLevelController;

  public ScreenController(Stage primaryStage, UserList users, BasicLevelList levels){
    myStage = primaryStage;
    myStage.setResizable(false);

    myUsers = users;
    mySelectedUser = users.getSelectedUser();
    myBasicLevels = levels;

    addApplicationIcon();
    initializeScreens();

    switchToScreen("HomeScreen");
  }

  private void addApplicationIcon() {
    try {
      Image icon = new Image(new FileInputStream(ARTWORK_GOOMBA_PNG));
      myStage.getIcons().add(icon);
    } catch (Exception ignored) {
      //TODO: add actual catch
    }
  }

  private void initializeScreens(){
    Screen myLevelSelectorScreen = new LevelSelectorScreen(this, myBasicLevels);
    //private Screen mySplashScreen = new SplashScreen();
    Screen myUserSelectorScreen = new UserSelectorScreen(this, myUsers);
    Screen myHomeScreen = new HomeScreen(this);
    //private Screen mySplashScreen = new SplashScreen();

    myScreens.put("HomeScreen", myHomeScreen);
    //myScreens.put("SplashScreen", mySplashScreen);
    myScreens.put("UserSelectorScreen", myUserSelectorScreen);
    myScreens.put("LevelSelectorScreen", myLevelSelectorScreen);
    //private Screen myLevelBuilderScreen = new LevelBuilderScreen();
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

  public void initializeNewLevel(BasicLevel basicLevel){
    myGameScreen = new GameScreen(this, basicLevel);
    myScreens.put("GameScreen", myGameScreen);

    myLevelController =
        new LevelController(myGameScreen, mySelectedUser, basicLevel);
    myGameScreen.setLevelController(myLevelController);

    Screen nextScreen = myScreens.get("GameScreen");
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    File file = new File("resources/stylesheet.css");
    nextScene.getStylesheets().add(file.toURI().toString());
    nextScene.setOnKeyPressed(myLevelController::handleKeyPressed);
    nextScene.setOnKeyReleased(myLevelController::handleKeyReleased);

    showScene(nextScene);

    myLevelController.beginLevel();
  }

  public UserList getUsers() {
    return myUsers;
  }

  public void setSelectedUser(User user){
    mySelectedUser = user;
    myUsers.setSelectedUser(user);
    initializeScreens();
  }

  public void restartLevel(){
    myLevelController.endLevel();
    myScreens.remove("GameScreen");

  }

}
