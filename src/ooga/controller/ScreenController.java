package ooga.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import javafx.scene.layout.Pane;
import ooga.model.data.User;
import ooga.view.Screen;

public class ScreenController{

  private Screen myGameScreen;
  private Screen myUserSelectorScreen;
  private Screen mySplashScreen;
  private Screen myLevelSelectorScreen;
  private Screen myLevelBuilderScreen;
  private Screen myHomeScreen;

  public ScreenController(Pane mainPane){
    
  }

  private void initializeScreens(){};

  public void handleButtonPress(){

  }

  public void switchToScreen(String screenName){};

  public void setUsers(List<User> myUsers) {

  }
}
