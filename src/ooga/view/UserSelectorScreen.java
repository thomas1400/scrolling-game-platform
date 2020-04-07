package ooga.view;

import java.util.Iterator;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ooga.controller.ScreenController;

public class UserSelectorScreen extends Screen {

  private ScreenController controller;

  public UserSelectorScreen(ScreenController controller) {
    this.controller = controller;
    initializeScreen();
    initializeLayout();
  }

  private void initializeLayout() {
    VBox layout = new VBox();

    layout.getChildren().add(new UserSelector(800, 600, ""));

    HBox menu = new HBox();

    layout.getChildren().add(menu);

    this.getChildren().add(layout);
  }


  private class UserSelector extends Pane { // add a new user

    private static final double PADDING = 5;
    private final int NUM_USERS = 3;
    private ToggleGroup users;

    UserSelector(double width, double height, String usersFilePath) {

      this.setPrefSize(width, height);

      users = new ToggleGroup();
      double buttonWidth = (width-(NUM_USERS+1)*PADDING) / NUM_USERS;
      double buttonHeight = height / NUM_USERS;
      for (int i = 0; i < NUM_USERS; i++) {
        RadioButton button = new RadioButton("User " + i);
        button.setPrefSize(buttonWidth, buttonHeight);
        button.setLayoutX(i * buttonWidth + PADDING);

        button.setToggleGroup(users);
        if (i == 0) {
          button.setSelected(true);
        }

        this.getChildren().add(button);
      }
    }

    String getSelected() {
      String info = users.getSelectedToggle().toString();
      return info.substring(info.indexOf('\'') + 1, info.lastIndexOf('\''));
    }
  }

}
