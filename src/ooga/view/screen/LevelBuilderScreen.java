package ooga.view.screen;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class LevelBuilderScreen extends Screen {

  private static final String CHECK_URL = "images/greenCheck.png";
  private static final String EX_URL = "images/redX.png";
  private static final String DEFAULT_URL = "images/grayAsterisk.png";

  private TextField levelTitle = new TextField();
  private ImageView levelTitleValidated = new ImageView(DEFAULT_URL);
  private TextField subTitle = new TextField();
  private ImageView subTitleValidated = new ImageView(DEFAULT_URL);
  private TextField background = new TextField();
  private ComboBox<String> physicsType = new ComboBox<>();
  private ImageView physicsValidated = new ImageView(DEFAULT_URL);
  private ComboBox<String> scrollType = new ComboBox<>();
  private ImageView scrollValidated = new ImageView(DEFAULT_URL);
  private TextField levelLives = new TextField();
  private ImageView levelLivesValidated = new ImageView(DEFAULT_URL);
  private TextField height = new TextField();
  private ImageView heightValidated = new ImageView(DEFAULT_URL);
  private TextField width = new TextField();
  private ImageView widthValidated = new ImageView(DEFAULT_URL);
  private ImageView validated = new ImageView(DEFAULT_URL);

  private FileChooser backgroundPicker = new FileChooser();
  private ScrollPane levelGrid = new ScrollPane();
  private ScrollPane entitySelector = new ScrollPane();
  

  public LevelBuilderScreen(ScreenController controller) {
    super(controller);
    this.getStyleClass().add("level-builder-screen");

    dynamicNodes.put("level-title-input", levelTitle);
    dynamicNodes.put("level-title-validate", levelTitleValidated);
    dynamicNodes.put("sub-title-input", subTitle);
    dynamicNodes.put("sub-title-validate", subTitleValidated);
    dynamicNodes.put("background-input", background);
    dynamicNodes.put("physics-type-input", physicsType);
    dynamicNodes.put("physics-type-validate", physicsValidated);
    dynamicNodes.put("scroll-type-input", scrollType);
    dynamicNodes.put("scroll-type-validate", scrollValidated);
    dynamicNodes.put("lives-input", levelLives);
    dynamicNodes.put("lives-validate", levelLivesValidated);
    dynamicNodes.put("height-input", height);
    dynamicNodes.put("height-validate", heightValidated);
    dynamicNodes.put("width-input", width);
    dynamicNodes.put("width-validate", widthValidated);
    dynamicNodes.put("level-editor", levelGrid);
    dynamicNodes.put("entity-editor", entitySelector);
    dynamicNodes.put("validated-image", validated);

    loadLayout();
  }

  public void makeGrid() {

  }
  public void validateLevel() {

  }
  public void saveLevel() {

  }
  public void clearLevel() {

  }
  public void chooseImage(){

  }


  private void getBackgroundFilePath(Label imagePath) throws IOException {
    FileChooser backgroundInput = new FileChooser();
    backgroundInput.setTitle("Open Resource File");
    backgroundInput.setInitialDirectory(new File("resources/images"));
    backgroundInput.getExtensionFilters().addAll(
        new ExtensionFilter("ImageView Files", "*.png", "*.jpg", "*.gif"));
    File selectedFile = backgroundInput.showOpenDialog(new Stage());
    if (selectedFile != null) {
      imagePath.setText(selectedFile.getName());
    }
  }

}
