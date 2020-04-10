package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ooga.controller.ScreenController;
import ooga.model.data.User;
import ooga.model.data.UserList;
import ooga.view.factory.ControlFactory;

public class UserSelectorScreen extends Screen {

  public UserSelectorScreen(ScreenController controller) {
    super(controller);
    setWorkingDimensions(3, 1);
    initializeLayout();
  }

  private void initializeLayout() {
    ControlFactory cf = new ControlFactory(PADDING);
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.BOTTOM_CENTER);

    Label title = cf.label(resources.getString("select-user"), TITLE_FONT_SIZE);
    cf.setMargin(title);
    vbox.getChildren().add(title);

    List<User> users = new ArrayList<>();
    UserSelector us = new UserSelector(workingWidth, workingHeight * 0.8, users);
    cf.setMargin(us);
    vbox.getChildren().add(us);

    Button begin = cf.button(
        resources.getString("begin"), BUTTON_FONT_SIZE,
        e->handleButtonPress("begin"), 100, 0.1*workingHeight
    );
    vbox.getChildren().add(begin);

    this.getChildren().add(vbox);
  }


  // TODO : add capability to add a new user from the user selection screen
  private class UserSelector extends Pane {

    private static final double PADDING = 10;
    private final int MAX_USERS = 3;
    private ToggleGroup userToggles;

    UserSelector(double width, double height, List<User> users) {

      this.setPrefSize(width, height);

      userToggles = new ToggleGroup();
      double buttonWidth = (width-(MAX_USERS-1)*PADDING) / MAX_USERS;
      double buttonHeight = height;
      for (int i = 0; i < MAX_USERS; i++) {
        ToggleButton button = new ToggleButton("User " + i);
        button.setLayoutX(i * (buttonWidth + PADDING));
        button.setPrefSize(buttonWidth, buttonHeight);
        button.setToggleGroup(userToggles);
        this.getChildren().add(button);
      }
    }

    String getSelected() {
      String info = userToggles.getSelectedToggle().toString();
      return info.substring(info.indexOf('\'') + 1, info.lastIndexOf('\''));
    }
  }

}
