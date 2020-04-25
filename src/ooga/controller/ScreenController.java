package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.levels.LevelLoader;
import ooga.controller.users.User;
import ooga.controller.users.UserList;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.screen.GameScreen;
import ooga.view.screen.GameSelectionScreen;
import ooga.view.screen.LevelSelectorScreen;
import ooga.view.screen.LoadingSplash;
import ooga.view.screen.Screen;
import ooga.view.screen.UserSelectorScreen;

public class ScreenController{

  public static final String ARTWORK_GOOMBA_PNG = "resources/applicationIcon.png";
  public static final String SCREEN_CLASS_PATH = "ooga.view.screen.";
  public static final String GAMEDATA_PATH = "data/gamedata/";
  public static final String GAME_STYLE_CSS = "/gameStyle.css";

  public static final String HOME_SCREEN = "HomeScreen";
  public static final String USER_SELECTOR_SCREEN = "UserSelectorScreen";
  public static final String GAME_SELECTION_SCREEN = "GameSelectionScreen";
  public static final String LEVEL_SELECTOR_SCREEN = "LevelSelectorScreen";

  private static final int INITIAL_WINDOW_WIDTH = 800;
  private static final int INITIAL_WINDOW_HEIGHT = 600;
  public static final File MAIN_STYLESHEET = new File("data/stylesheet.css");

  private Stage myStage;
  private Screen myCurrentScreen;
  private UserList myUsers;
  private User mySelectedUser;
  private BasicLevelList myBasicLevels;
  private String myGameType;
  private BasicLevel myCurrentLevel;

  private GameScreen myGameScreen;
  private LevelController myLevelController;

  /**
   * Creates the singular ScreenController for the game. Handles switching between different
   * screens in the game. Initially sets things to the GameSelectionScreen
   * @param primaryStage stage on which the scenes are displayed
   * @param users list of users who's properties can be used for game-play and user-selection
   */
  public ScreenController(Stage primaryStage, UserList users) {
    myStage = primaryStage;
    myStage.setResizable(false);
    myUsers = users;
    mySelectedUser = users.getSelectedUser();

    addApplicationIcon();

    switchToScreen(GAME_SELECTION_SCREEN);
  }

  private void addApplicationIcon() {
    try {
      Image icon = new Image(new FileInputStream(ARTWORK_GOOMBA_PNG));
      myStage.getIcons().add(icon);
    } catch (Exception e) {
      ExceptionFeedback.throwBreakingException(e, "App Icon Not Found (Screen Controller)");
    }
  }

  /**
   * @param screenName string name of the screen to be initialized and switched to
   */
  public void switchToScreen(String screenName){
    Screen nextScreen = null;
    try {
      nextScreen = getScreen(screenName);
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException | ClassNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, "Screen " + screenName + " unable to be "
          + "reflexively created!");
    }
    Scene nextScene = getScene(nextScreen);
    showScene(nextScene);
  }

  private Screen getScreen(String screenName)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
    if (screenName.equals(LEVEL_SELECTOR_SCREEN)){
      return new LevelSelectorScreen(this, myBasicLevels);
    } else if (screenName.equals(USER_SELECTOR_SCREEN)){
      return new UserSelectorScreen(this, myUsers);
    } else {
      Class screenClass = Class.forName(SCREEN_CLASS_PATH + screenName);
      Constructor screenClassConstructor = screenClass.getConstructor(ScreenController.class);
      return (Screen) screenClassConstructor.newInstance(this);
    }
  }

  private Scene getScene(Screen nextScreen) {
    this.myCurrentScreen = nextScreen;
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    nextScene.getStylesheets().add(MAIN_STYLESHEET.toURI().toString());
    if (myGameType != null) {
      File gameSpecificStyle = new File(GAMEDATA_PATH + myGameType + GAME_STYLE_CSS);
      if (nextScreen instanceof GameSelectionScreen) {
        nextScene.getStylesheets().removeAll(gameSpecificStyle.toURI().toString());
      } else {
        nextScene.getStylesheets().add(gameSpecificStyle.toURI().toString());
      }
    }
    return nextScene;
  }

  private void showScene(Scene nextScene) {
    Scene lastScene = myStage.getScene();
    myStage.setScene(nextScene);
    if (lastScene != null) {
      lastScene.setRoot(new Pane());
    }
    myStage.show();
  }

  /**
   * Creates a new GameScreen and switches to it to begin game-play
   * @param basicLevel The basic level information needed to begin the creation of a new level
   */
  public void initializeNewLevel(BasicLevel basicLevel){
    Pane loadingPane = new LoadingSplash(this, myCurrentScreen, basicLevel);
    myCurrentScreen.getChildren().add(loadingPane);

    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), loadingPane);

    fadeIn.setOnFinished((e) -> {
      myCurrentLevel = basicLevel;

      myGameScreen = new GameScreen(this, myCurrentLevel);

      myLevelController = new LevelController(myGameScreen, mySelectedUser, basicLevel);
      myGameScreen.setLevelController(myLevelController);

      Scene nextScene = getScene(myGameScreen);
      nextScene.setOnKeyPressed(myLevelController::handleKeyPressed);
      nextScene.setOnKeyReleased(myLevelController::handleKeyReleased);

      showScene(nextScene);
      myLevelController.begin();
    });

    doFade(fadeIn);
  }

  /**
   * @param user is set as the selectedUser in the global UserList
   */
  public void setSelectedUser(User user){
    mySelectedUser = user;
    myUsers.setSelectedUser(user);
  }

  /**
   * restarts the game level if the reset button is pressed
   */
  public void restartLevel(){
    myLevelController.endLevel(false);

    Screen loadingPane = new LoadingSplash(this, myGameScreen, myCurrentLevel);
    myGameScreen.getChildren().add(loadingPane);

    FadeTransition fade = new FadeTransition(Duration.seconds(0.5), loadingPane);
    doFade(fade);
    fade.setOnFinished((e) -> initializeNewLevel(myCurrentLevel));
  }

  private void doFade(FadeTransition fadeIn) {
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.setCycleCount(1);
    fadeIn.play();
  }

  /**
   * @param gameType to be set as the game type and used to determine the resource path of levels
   *                and other game related content.
   */
  public void setGame(String gameType)  {
    if (gameType != null) {
      myGameType = gameType;
      myBasicLevels = new BasicLevelList();
      LevelLoader.loadLevels(gameType, myBasicLevels);
    } else {
      ExceptionFeedback.throwHandledException(new RuntimeException(), "Game type not received.\n"
          + "Screen creation cannot continue :(\nPlease choose a game!");
    }
    switchToScreen(HOME_SCREEN);
  }

  /**
   * @return the current game's type
   */
  public String getGameType() {
    return myGameType;
  }

  /**
   * @return the UserList of Users that have been created/loaded into the game
   */
  public UserList getUsers() {
    return myUsers;
  }

  public ObservableList<String> getCameraManagerOptions() {
    // TODO : get camera manager options
    return FXCollections.observableArrayList("Right-Direction", "Left-Direction", "Centered");
  }

  public void setCameraManagerOption(String selected) {
    // TODO : set camera manager
  }

  public ObservableList<String> getPhysicsOptions() {
    // TODO : get physics options
    return FXCollections.observableArrayList("Land", "Water", "Floating", "Flying");
  }

  public void setPhysicsOption(String selected) {
    // TODO : set physics option
  }
}
