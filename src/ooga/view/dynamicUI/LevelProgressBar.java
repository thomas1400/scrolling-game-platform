package ooga.view.dynamicUI;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelProgressBar extends Pane {

  public static final int BAR_LAYOUT_OFFSET = 1;
  private double levelProgressFraction;

  public LevelProgressBar(int levelProgress, int totalLevels) {
    levelProgressFraction = (float) levelProgress / totalLevels;

    this.getStyleClass().add("level-progress-bar");
  }

  private void initializeBar(double width, double height) {
    Rectangle bar = new Rectangle(width * (float) levelProgressFraction - 2, height - 2);
    bar.setX(BAR_LAYOUT_OFFSET);
    bar.setY(BAR_LAYOUT_OFFSET);
    bar.getStyleClass().add("dynamic-ui-filled");
    this.getChildren().add(bar);
  }

  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    this.getChildren().clear();
    initializeBar(width, height);
  }

}