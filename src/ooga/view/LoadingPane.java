package ooga.view;

import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.swing.plaf.synth.SynthStyle;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class LoadingPane extends Pane {

  private static final Color BACKGROUND = Color.BLACK;
  private static final Color TEXT_COLOR = Color.WHITE;
  private static final double PADDING = 10.0;
  private static final int FONT_SIZE = 25;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String RESOURCES_SUFFIX = "Text";

  public LoadingPane(Node parent) {
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());
    initializeLayout();
  }

  private void initializeLayout() {
    this.setBackground(new Background(new BackgroundFill(BACKGROUND, null, null)));

    ControlFactory cf = new ControlFactory(PADDING);
    ResourceBundle resources = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + RESOURCES_SUFFIX
    );
    Label loading = cf.label(resources.getString("loading"), FONT_SIZE);
    loading.setTextFill(TEXT_COLOR);
    loading.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
    loading.setAlignment(Pos.CENTER);

    Label level = cf.label(resources.getString("level"), FONT_SIZE);
    loading.setTextFill(TEXT_COLOR);
    loading.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
    loading.setAlignment(Pos.CENTER);

    this.getChildren().add(loading);
  }

}
