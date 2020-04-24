package ooga.view.dynamicUI;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelProgressBar extends Pane {

  private static final int BAR_LAYOUT_OFFSET = 1;
  private double levelProgressFraction;
  private Region bar;
  private Label label;

  public LevelProgressBar(String text, int levelProgress, int totalLevels) {
    levelProgressFraction = (float) Math.min(levelProgress, totalLevels) / totalLevels;
    bar = new Region();
    bar.setLayoutX(BAR_LAYOUT_OFFSET);
    bar.setLayoutY(BAR_LAYOUT_OFFSET);
    bar.getStyleClass().add("bar");
    this.getChildren().add(bar);

    label = new Label(text);
    label.setBlendMode(BlendMode.DIFFERENCE);
    label.setAlignment(Pos.CENTER);
    this.getChildren().add(label);

    this.getStyleClass().add("level-progress-bar");
  }

  public void setPreferredWidth(double width) {
    this.setPrefWidth(width);
    bar.setPrefWidth(width * (float) levelProgressFraction - 2);
    label.setPrefWidth(width);
  }

  public void setPreferredHeight(double height) {
    this.setPrefHeight(height);
    bar.setPrefHeight(height - 2);
    label.setPrefHeight(height);
  }

  double getBarWidth() {
    return bar.getPrefWidth();
  }
}