package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.controller.data.User;
import ooga.controller.data.UserList;
import ooga.view.dynamicUI.UserSelector;
import ooga.view.factory.ControlFactory;

public class UserSelectorScreen extends Screen {

  private UserList myUsers;
  private UserSelector us;

  public UserSelectorScreen(ScreenController controller, UserList users) {
    super(controller);
    myUsers = users;
    us = new UserSelector(controller.getUsers());

    dynamicNodes.put("user-selector", us);

    loadLayout();
  }

  public void selectUser() {
    if (us.getSelected() != null) {
      controller.setSelectedUser(us.getSelected());
    }
    controller.switchToScreen("HomeScreen");
  }

  // TODO : add capability to add a new user from the user selection screen

}
