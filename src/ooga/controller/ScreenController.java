package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
import ooga.controller.users.User;
import ooga.controller.users.UserList;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.screen.GameScreen;
import ooga.view.screen.LevelSelectorScreen;
import ooga.view.screen.LoadingScreen;
import ooga.view.screen.Screen;
import ooga.view.screen.UserSelectorScreen;

public class ScreenController{

  private static final int INITIAL_WINDOW_WIDTH = 800;
  private static final int INITIAL_WINDOW_HEIGHT = 600;
  public static final File MAIN_STYLESHEET = new File("data/stylesheet.css");
  public static final String ARTWORK_GOOMBA_PNG = "resources/applicationIcon.png";

  private Stage myStage;
  private UserList myUsers;
  private User mySelectedUser;
  private BasicLevelList myBasicLevels;
  private String myGameType;
  private BasicLevel myCurrentLevel;

  private Map<String, Screen> myScreens = new HashMap<>();

  private GameScreen myGameScreen;
  private LevelController myLevelController;

  public ScreenController(Stage primaryStage, UserList users) {
    myStage = primaryStage;
    myStage.setResizable(false);

    myUsers = users;
    mySelectedUser = users.getSelectedUser();

    addApplicationIcon();

    //FIXME: Should remove this game type line and load levels line when game selector screen works
    myGameType = "mario";
    try {
      myBasicLevels = new BasicLevelList();
      loadLevels(myGameType);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      ExceptionFeedback.throwBreakingException(new RuntimeException(),
          "Game type " + myGameType + " is invalid.");
    }

    //switchToScreen("HomeScreen");
    switchToScreen("GameSelectionScreen");
  }

  private void addApplicationIcon() {
    try {
      Image icon = new Image(new FileInputStream(ARTWORK_GOOMBA_PNG));
      myStage.getIcons().add(icon);
    } catch (Exception e) {
      ExceptionFeedback.throwBreakingException(e, "App Icon Not Found (Screen Controller)");
    }
  }

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
    if (screenName.equals("LevelSelectorScreen")){
      return new LevelSelectorScreen(this, myBasicLevels);
    } else if (screenName.equals("UserSelectorScreen")){
      return new UserSelectorScreen(this, myUsers);
    } else {
      Class screenClass = Class.forName("ooga.view.screen." + screenName);
      Constructor screenClassConstructor = screenClass.getConstructor(ScreenController.class);
      return (Screen) screenClassConstructor.newInstance(this);
    }
  }

  private void showScene(Scene nextScene) {
    Scene lastScene = myStage.getScene();
    myStage.setScene(nextScene);
    if (lastScene != null) {
      lastScene.setRoot(new Pane());
    }
    myStage.show();
  }

  private Scene getScene(Screen nextScreen) {
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    nextScene.getStylesheets().add(MAIN_STYLESHEET.toURI().toString());
    File gameSpecificStyle = new File("data/gamedata/" + myGameType + "/gameStyle.css");
    nextScene.getStylesheets().add(gameSpecificStyle.toURI().toString());
    return nextScene;
  }

  public void initializeNewLevel(BasicLevel basicLevel){
    myCurrentLevel = basicLevel;
    myGameScreen = new GameScreen(this, myCurrentLevel);
    myScreens.put("GameScreen", myGameScreen);

    myLevelController =
        new LevelController(myGameScreen, mySelectedUser, basicLevel);
    myGameScreen.setLevelController(myLevelController);

    Screen nextScreen = myScreens.get("GameScreen");
    Scene nextScene = new Scene(nextScreen, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
    File file = new File("data/stylesheet.css");
    nextScene.getStylesheets().add(file.toURI().toString());
    File gameSpecificStyle = new File("data/gamedata/" + myGameType + "/gameStyle.css");
    nextScene.getStylesheets().add(gameSpecificStyle.toURI().toString());

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
  }

  public void restartLevel(){
    myLevelController.endLevel();

    Pane loadingPane = new LoadingScreen(this, myGameScreen, myCurrentLevel);
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

  public void setGame(String gameType) throws FileNotFoundException {
    if (gameType != null) {
      myGameType = gameType;
      myBasicLevels = new BasicLevelList();
      loadLevels(gameType);
    } else {
      ExceptionFeedback.throwHandledException(new RuntimeException(), "Game type not received.\n"
          + "Screen creation cannot continue :(\nPlease choose a game!");
    }
    switchToScreen("HomeScreen");
  }

  private void loadLevels(String myGameType) throws FileNotFoundException {
    ResourceBundle myLevelsBundle = ResourceBundle.getBundle(
        "gamedata/"+myGameType+"/levels/resources/levelOrder");

    String[] levelNumbers = myLevelsBundle.getString("levelNumbers").split(",");

    for (String levelNumberString : levelNumbers) {
      int levelNumber = Integer.parseInt(levelNumberString);
      File levelFile =
          new File("data/gamedata/"+myGameType+"/levels/" + myLevelsBundle.getString(levelNumberString) +
              ".level");
      myBasicLevels.addBasicLevel(LevelBuilder.buildBasicLevel(levelNumber, levelFile));
    }
  }

  public String getGameType() {
    return myGameType;
  }

}
