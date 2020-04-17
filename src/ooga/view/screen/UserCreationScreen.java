package ooga.view.screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import ooga.controller.ScreenController;
import ooga.controller.data.User;
import ooga.exceptions.ExceptionFeedback;

public class UserCreationScreen extends Screen {

  private TextField nameField;
  private FileChooser imageChooser;
  private File imageFile;

  public UserCreationScreen(ScreenController controller) {
    super(controller);
    this.getStyleClass().add("user-creation-screen");

    nameField = new TextField();
    imageChooser = new FileChooser();

    dynamicNodes.put("name-field", nameField);

    loadLayout();
  }

  public void chooseImage() {
    imageFile = imageChooser.showOpenDialog(new Stage());
  }

  public void save() {
    if (imageFile != null && !nameField.getText().equals("")) {
      //controller.getUsers().addUser(new User(nameField.getText(), imageFile.getPath(), new ArrayList<>(), 3));
      controller.switchToScreen("HomeScreen");
    } else {
      ExceptionFeedback.throwHandledException(new FileNotFoundException(),
          "Please select a name and image file for your new user.");
    }
  }
}
