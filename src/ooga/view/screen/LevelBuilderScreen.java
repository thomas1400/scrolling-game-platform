package ooga.view.screen;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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

  private static final int MEDIUM_TEXT_SIZE = 22;
  ControlFactory cf;

  public LevelBuilderScreen(ScreenController controller) {
    super(controller);
    setWorkingDimensions(3, 1);
    initializeLayout();
  }

  private void initializeLayout() {

    cf = new ControlFactory(PADDING);
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(PADDING);
    this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

    //TITLE BOX
    HBox titleBox = new HBox();
    Label screenTitle = cf.label("LevelBuilder", TITLE_FONT_SIZE);
    titleBox.getChildren().add(screenTitle);

    //LEVEL OPTIONS BOX
    HBox levelOptions = new HBox();
    VBox generalOptions = getGeneralOptionsBox();
    VBox specificOptions = getSpecificOptionsBox();
    VBox gridOptions = getGridOptionsBox();
    levelOptions.getChildren().addAll(generalOptions, specificOptions, gridOptions);

    //LEVEL CREATION BOX
    HBox levelCreation = new HBox();
    ScrollPane levelEditor = getLevelEditor();
    VBox entityEditor = getEntityEditor();
    levelCreation.getChildren().addAll(levelEditor, entityEditor);

    //LEVEL SUBMISSION BOX
    HBox levelSubmission = new HBox();
    Button validateButton = cf.button("VALIDATE", MEDIUM_TEXT_SIZE, e -> validateLevel(), 150, 50);
    Button saveButton = cf.button("SAVE LEVEL", MEDIUM_TEXT_SIZE, e -> saveLevel(), 150,
        50);
    Button clearButton = cf.button("CLEAR ALL", MEDIUM_TEXT_SIZE, e -> clearLevel(), 150, 50);
    levelSubmission.getChildren().addAll(validateButton, saveButton, clearButton);

    layout.getChildren().addAll(titleBox, levelOptions, levelCreation, levelSubmission);
    this.getChildren().add(layout);
  }

  private void clearLevel() {
  }

  private void saveLevel() {
  }

  private void validateLevel() {

  }

  private VBox getEntityEditor() {

    Label entityEditorLabel = cf.label("Entity Selector", MEDIUM_TEXT_SIZE);

    ScrollPane entityEditorPane = new ScrollPane();
    entityEditorPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    return new VBox(entityEditorLabel, entityEditorPane);
  }

  private ScrollPane getLevelEditor() {

    ScrollPane levelEditor = new ScrollPane();
    Group levelGrid = new Group();

    levelEditor.setHbarPolicy(ScrollBarPolicy.ALWAYS);
    levelEditor.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    levelEditor.setContent(levelGrid);
    levelEditor.setPrefViewportHeight(380);
    levelEditor.setPrefViewportWidth(550);

    return levelEditor;
  }

  private VBox getGeneralOptionsBox() {

    HBox levelTitleBox = new HBox();
    Label levelTitle = cf.label("Level Title: ", DETAIL_FONT_SIZE);
    TextField levelTitleInput = new TextField();
    levelTitleBox.getChildren().addAll(levelTitle, levelTitleInput);

    HBox subTitleBox = new HBox();
    Label subTitle = cf.label("Sub Title: ", DETAIL_FONT_SIZE);
    TextField subTitleInput = new TextField();
    subTitleBox.getChildren().addAll(subTitle, subTitleInput);

    HBox backgroundBox = new HBox();
    Label background = cf.label("Background: ", DETAIL_FONT_SIZE);
    Label imagePath = cf.label("", DETAIL_FONT_SIZE);
    Button selectorButton = cf.button("Choose", 10, e -> {
      try {
        getBackgroundFilePath(imagePath);
      } catch (IOException ex) {
        //FIXME LET'S NOT FAIL THE CLASS :))
        ex.printStackTrace();
      }
    }, 60, 12);
    backgroundBox.getChildren().addAll(background, imagePath, selectorButton);

    return new VBox(levelTitleBox, subTitleBox, backgroundBox);
  }

  private void getBackgroundFilePath(Label imagePath) throws IOException {
    FileChooser backgroundInput = new FileChooser();
    backgroundInput.setTitle("Open Resource File");
    backgroundInput.setInitialDirectory(new File("resources/images"));
    backgroundInput.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    File selectedFile = backgroundInput.showOpenDialog(new Stage());
    if (selectedFile != null) {
      imagePath.setText(selectedFile.getName());
    }
  }

  private VBox getSpecificOptionsBox() {

    HBox physicsTypeBox = new HBox();
    Label physicsLabel = cf.label("Physics Type: ", DETAIL_FONT_SIZE);
    ComboBox<String> physicsTypeInput = new ComboBox<>();
    physicsTypeBox.getChildren().addAll(physicsLabel, physicsTypeInput);

    HBox scrollTypeBox = new HBox();
    Label scrollLabel = cf.label("Scroll Type: ", DETAIL_FONT_SIZE);
    ComboBox<String> scrollTypeInput = new ComboBox<>();
    scrollTypeBox.getChildren().addAll(scrollLabel, scrollTypeInput);

    HBox deathsAllowedBox = new HBox();
    Label deathsTitle = cf.label("Deaths Allowed: ", DETAIL_FONT_SIZE);
    TextField deathsTitleInput = new TextField();
    deathsAllowedBox.getChildren().addAll(deathsTitle, deathsTitleInput);

    return new VBox(physicsTypeBox, scrollTypeBox, deathsAllowedBox);
  }

  private VBox getGridOptionsBox() {
    HBox heightBox = new HBox();
    Label heightLabel = cf.label("Level Height: ", DETAIL_FONT_SIZE);
    TextField heightInput = new TextField();
    heightBox.getChildren().addAll(heightLabel, heightInput);

    HBox widthBox = new HBox();
    Label widthLabel = cf.label("Level Width: ", DETAIL_FONT_SIZE);
    TextField widthInput = new TextField();
    widthBox.getChildren().addAll(widthLabel, widthInput);

    Button gridButton = cf.button("MAKE GRID", DETAIL_FONT_SIZE, e-> setGrid(), 150, 12);

    return new VBox(heightBox, widthBox, gridButton);
  }

  private void setGrid() {
  }

}
