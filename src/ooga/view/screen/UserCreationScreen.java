package ooga.view.screen;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.UserSaver;
import ooga.controller.users.User;
import ooga.exceptions.ExceptionFeedback;

/**
 * Screen that enables new User creation.
 */
public class UserCreationScreen extends Screen {

  private TextField nameField;
  private FileChooser imageChooser;
  private File imageFile;

  /**
   * Initializes a UserCreationScreen. Called via REFLECTION.
   * @param controller ScreenController
   */
  public UserCreationScreen(ScreenController controller) {
    super(controller);
    this.getStyleClass().add("user-creation-screen");

    nameField = new TextField();
    imageChooser = new FileChooser();

    dynamicNodes.put("name-field", nameField);

    loadLayout();
  }

  /**
   * Choose an image for a new user. Public for REFLECTION.
   */
  public void chooseImage() {
    imageFile = imageChooser.showOpenDialog(new Stage());
  }

  /**
   * Save the newly created user to the ScreenController, checking for field completion.
   */
  public void save() {
    if (imageFile != null && !nameField.getText().equals("")) {
      User newUser = new User(nameField.getText(), imageFile.toURI().toString());
      UserSaver.saveUser(newUser);
      controller.getUsers().addUser(newUser);
      controller.setSelectedUser(newUser);
      controller.switchToScreen("HomeScreen");
    } else {
      ExceptionFeedback.throwHandledException(new FileNotFoundException(),
          "Please select a name and image file for your new user.");
    }
  }
}
