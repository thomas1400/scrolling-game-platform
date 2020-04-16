package ooga.view.dynamicUI;


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

  public static final int BAR_LAYOUT_OFFSET = 1;
  private double levelProgressFraction;
  private Region bar;

  public LevelProgressBar(int levelProgress, int totalLevels) {
    levelProgressFraction = (float) levelProgress / totalLevels;
    bar = new Region();
    bar.setLayoutX(BAR_LAYOUT_OFFSET);
    bar.setLayoutY(BAR_LAYOUT_OFFSET);
    bar.getStyleClass().add("bar");
    this.getChildren().add(bar);

    this.getStyleClass().add("level-progress-bar");
  }

  @Override
  public void setWidth(double width) {
    super.setWidth(width);
    bar.setPrefWidth(width * (float) levelProgressFraction - 2);
  }

  @Override
  public void setHeight(double height) {
    super.setHeight(height);
    bar.setPrefHeight(height - 2);
  }

}