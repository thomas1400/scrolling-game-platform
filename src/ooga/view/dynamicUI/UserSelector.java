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
import ooga.controller.data.User;
import ooga.controller.data.UserList;

public class UserSelector extends Pane {

  private static final double SPACING = 10;
  private static final double TILES_PER_PAGE = 3;
  private static final double SCROLL_BAR_OFFSET = 50.0;
  private ToggleGroup userToggles;
  private List<User> users;
  private ScrollPane scrollPane;
  private HBox usersPane;
  private double tileSize;

  public UserSelector(UserList userList) {
    userToggles = new ToggleGroup();
    scrollPane = new ScrollPane();
    usersPane = new HBox();
    usersPane.setAlignment(CENTER_LEFT);
    usersPane.setSpacing(SPACING);
    scrollPane.setContent(usersPane);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

    users = new ArrayList<>();
    if (userList != null) {
      for (User user : userList) {
        users.add(user);
      }
    }

    initializeButtons();
    this.getChildren().add(scrollPane);
    this.getStyleClass().add("user-selector");
  }

  private void initializeButtons() {
    int numUsers = users.size();
    tileSize = this.getPrefWidth()/(TILES_PER_PAGE) - SPACING;

    for (int i = 0; i < numUsers; i++) {

      ImageView image = new ImageView(users.get(i).getImage());
      image.setFitHeight(tileSize);
      image.setFitWidth(tileSize);

      ToggleButton button = new ToggleButton();
      button.setGraphic(image);
      button.setId(Integer.toString(i));
      button.setToggleGroup(userToggles);
      button.setPrefSize(tileSize, tileSize);

      Label username = new Label(users.get(i).getName());
      username.setPrefWidth(button.getPrefWidth());
      username.setAlignment(Pos.TOP_CENTER);
      username.getStyleClass().add("user-select-toggle");
      username.setMinHeight(SCROLL_BAR_OFFSET);

      VBox buttonBox = new VBox();
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.getChildren().add(button);
      buttonBox.getChildren().add(username);
      usersPane.getChildren().add(buttonBox);
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
    scrollPane.setPrefWidth(width);
  }

  public void setPaneHeight(double height) {
    setPrefHeight(height);
    reinitializeButtons();
  }

  private void reinitializeButtons() {
    usersPane.getChildren().clear();
    initializeButtons();
  }

}