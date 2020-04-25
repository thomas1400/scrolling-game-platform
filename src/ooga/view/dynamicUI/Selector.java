package ooga.view.dynamicUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class Selector extends Pane {

  private static final double SPACING = 10;
  private static final double TILES_PER_PAGE = 3;
  private static final double SCROLL_BAR_OFFSET = 50.0;

  HBox selectionPane;
  private ScrollPane scrollPane;
  ToggleGroup toggles;
  private double tileSize = 0;

  public Selector() {
    toggles = new ToggleGroup();
    scrollPane = new ScrollPane();
    selectionPane = new HBox();
    selectionPane.setAlignment(Pos.CENTER);
    selectionPane.setSpacing(SPACING);
    scrollPane.setContent(selectionPane);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

    this.getChildren().add(scrollPane);
  }

  /**
   * Set the pane width. Used for FXLR layout management.
   * @param width double width
   */
  public void setPaneWidth(double width) {
    setPrefWidth(width);
    scrollPane.setPrefWidth(width);
    reinitializeButtons();
  }

  /**
   * Set the pane height. Used for FXLR layout management.
   * @param height double height
   */
  public void setPaneHeight(double height) {
    setPrefHeight(height);
    reinitializeButtons();
  }

  private void reinitializeButtons() {
    setToggleSize();
    selectionPane.getChildren().clear();
    initializeButtons();
  }

  protected abstract void initializeButtons();

  ToggleButton makeToggleButton(String key, ImageView image) {
    ToggleButton button = new ToggleButton();
    button.setGraphic(image);
    button.setId(key);
    button.setToggleGroup(toggles);
    button.setPrefSize(tileSize, tileSize);
    return button;
  }

  Label makeToggleLabel(String text, ToggleButton button) {
    Label gameName = new Label(text);
    gameName.setPrefWidth(button.getPrefWidth());
    gameName.setAlignment(Pos.TOP_CENTER);
    gameName.setMinHeight(SCROLL_BAR_OFFSET);
    return gameName;
  }

  ImageView makeToggleGraphic(Image img) {
    ImageView image = new ImageView(img);
    image.setFitHeight(tileSize);
    image.setFitWidth(tileSize);
    return image;
  }

  void setToggleSize() {
    this.tileSize = this.getPrefWidth() / (TILES_PER_PAGE) - SPACING;
  }

  void makeButtonBox(ToggleButton button, Label gameName) {
    VBox buttonBox = new VBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().add(button);
    buttonBox.getChildren().add(gameName);
    selectionPane.getChildren().add(buttonBox);
  }


}
