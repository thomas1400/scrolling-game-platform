package ooga.view.dynamicUI;

import static javafx.geometry.Pos.CENTER_LEFT;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ooga.controller.users.User;
import ooga.controller.users.UserList;

/**
 * A custom-built UI control for selecting a user from a list of options.
 *
 * Shows user ToggleButtons with a graphic and name, enables getting the selected user.
 *
 * To use in a layout, add the dynamicUI package and put an instance in the Screen's
 * dynamicNodes map. Layout in FXLR using the dynamicNodes tag.
 */
public class UserSelector extends Selector {

  private List<User> users;

  /**
   * Create a new UserSelector with the given list of users.
   * @param userList list of users
   */
  public UserSelector(UserList userList) {

    users = new ArrayList<>();
    if (userList != null) {
      for (User user : userList) {
        users.add(user);
      }
    }

    initializeButtons();
    this.getStyleClass().add("user-selector");
    selectionPane.getStyleClass().add("users");
  }

  @Override
  protected void initializeButtons() {

    for (int i = 0; i < users.size(); i++) {

      ImageView image = makeToggleGraphic(users.get(i).getImage());
      ToggleButton button = makeToggleButton(Integer.toString(i), image);

      Label username = makeToggleLabel(users.get(i).getName(), button);
      username.getStyleClass().add("user-select-toggle");

      makeButtonBox(button, username);
    }
  }

  /**
   * Gets the selected user.
   * @return selected
   */
  public User getSelected() {
    Toggle selected = toggles.getSelectedToggle();
    if (selected != null) {
      return users.get(Integer.parseInt(((ToggleButton) selected).getId()));
    } else {
      return null;
    }
  }

}