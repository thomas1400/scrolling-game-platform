package ooga.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Class created by OogaController and set as the ScreenController's mainPane
 * Used by Screens to switch between screens on the main display
 */
public class MainDisplayPane extends Pane {

  public MainDisplayPane(){
    super();
  }

  /**
   * Called by a screen to switch the displayed screen to another one
   * @param screen
   */
  public void displayScreen(Screen screen){
    Node toDisplay = screen.getDisplay();
    getChildren().clear();
    getChildren().add(toDisplay);
  }

}
