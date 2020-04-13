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
import ooga.view.factory.ControlFactory;

public class UserSelectorScreen extends Screen {

  private UserList myUsers;

  public UserSelectorScreen(ScreenController controller, UserList users) {
    super(controller);
    myUsers = users;
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

    UserSelector us = new UserSelector(workingWidth, workingHeight * 0.8, controller.getUsers());
    cf.setMargin(us);
    vbox.getChildren().add(us);

    Button begin = cf.button(
        resources.getString("begin"), BUTTON_FONT_SIZE,
        e-> { if (us.getSelected() != null) {controller.setSelectedUser(us.getSelected());} handleButtonPress("begin"); },
        100, 0.1*workingHeight
    );
    vbox.getChildren().add(begin);

    this.getChildren().add(vbox);
  }

  // TODO : add capability to add a new user from the user selection screen
  private static class UserSelector extends Pane {

    private static final double PADDING = 10;
    private final int MAX_USERS = 4;
    private ToggleGroup userToggles;
    private List<User> users;

    UserSelector(double width, double height, UserList userList) {
      this.setPrefSize(width, height);
      userToggles = new ToggleGroup();

      users = new ArrayList<>();
      if (userList != null) {
        for (User user : userList) {
          users.add(user);
        }
      }

      int numUsers = Math.min(MAX_USERS, users.size());
      double buttonWidth = (width-(numUsers-1)*PADDING) / numUsers;

      for (int i = 0; i < numUsers; i++) {
        ImageView image = new ImageView(users.get(i).getImage());
        double imageSize = Math.min(0.5*height, 0.8*buttonWidth);
        image.setFitHeight(imageSize);
        image.setFitWidth(imageSize);
        ToggleButton button = new ToggleButton();
        button.setGraphic(image);

        button.setId(Integer.toString(i));

        button.setLayoutX(i * (buttonWidth + PADDING));
        button.setPrefSize(buttonWidth, height);
        button.setToggleGroup(userToggles);
        this.getChildren().add(button);

        // TODO : find a better way to add a label to the user selection button
        // TODO : maybe make a custom ToggleButton? If so, how?
        Label username = new Label(users.get(i).getName());
        username.setLayoutX(i* (buttonWidth + PADDING));
        username.setLayoutY(height * 0.75);
        username.setPrefWidth(buttonWidth);
        username.setAlignment(Pos.CENTER);
        username.setFont(Font.font(FONT_FAMILY, BUTTON_FONT_SIZE));
        this.getChildren().add(username);
      }
    }

    User getSelected() {
      Toggle selected = userToggles.getSelectedToggle();
      if (selected != null) {
        return users.get(Integer.parseInt(((ToggleButton) selected).getId()));
      } else {
        return null;
      }
    }
  }

}
