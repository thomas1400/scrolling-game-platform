package ooga.view.dynamicUI;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ooga.controller.data.User;
import ooga.controller.data.UserList;

public class UserSelector extends Pane {

  private static final double PADDING = 10;
  private final int MAX_USERS = 4;
  private ToggleGroup userToggles;
  private List<User> users;

  public UserSelector(UserList userList) {
    userToggles = new ToggleGroup();

    users = new ArrayList<>();
    if (userList != null) {
      for (User user : userList) {
        users.add(user);
      }
    }

    initializeButtons();
  }

  private void initializeButtons() {
    int numUsers = Math.min(MAX_USERS, users.size());
    double buttonWidth = (getPrefWidth()-(numUsers-1)*PADDING) / numUsers;

    for (int i = 0; i < numUsers; i++) {
      ImageView image = new ImageView(users.get(i).getImage());
      double imageSize = Math.min(0.5*getPrefHeight(), 0.8*buttonWidth);
      image.setFitHeight(imageSize);
      image.setFitWidth(imageSize);
      ToggleButton button = new ToggleButton();
      button.setGraphic(image);

      button.setId(Integer.toString(i));

      button.setLayoutX(i * (buttonWidth + PADDING));
      button.setPrefSize(buttonWidth, getPrefHeight());
      button.setToggleGroup(userToggles);
      this.getChildren().add(button);

      // TODO : find a better way to add a label to the user selection button
      // TODO : maybe make a custom ToggleButton? If so, how?
      Label username = new Label(users.get(i).getName());
      username.setLayoutX(i* (buttonWidth + PADDING));
      username.setLayoutY(getPrefHeight() * 0.75);
      username.setPrefWidth(buttonWidth);
      username.setAlignment(Pos.CENTER);
      username.getStyleClass().add("user-select-toggle");
      this.getChildren().add(username);
    }
  }

  public User getSelected() {
    Toggle selected = userToggles.getSelectedToggle();
    if (selected != null) {
      return users.get(Integer.parseInt(((ToggleButton) selected).getId()));
    } else {
      return null;
    }
  }

  public void setPaneWidth(double width) {
    setPrefWidth(width);
    reinitializeButtons();
  }

  public void setPaneHeight(double height) {
    setPrefHeight(height);
    reinitializeButtons();
  }

  private void reinitializeButtons() {
    this.getChildren().clear();
    initializeButtons();
  }

}