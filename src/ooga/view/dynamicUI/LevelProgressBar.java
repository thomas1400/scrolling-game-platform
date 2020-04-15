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

  private final Color background = Color.WHITE;
  private final Color fill = Color.DARKGREY;
  private final Color border = Color.BLACK;
  private double levelProgressFraction;

  public LevelProgressBar(int levelProgress, int totalLevels) {
    levelProgressFraction = (float) levelProgress / totalLevels;

    this.setBackground(new Background(new BackgroundFill(background, null, null)));


    this.setBorder(new Border(new BorderStroke(border,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
  }

  private void initializeBar(double width, double height) {
    Rectangle bar = new Rectangle(width * (float) levelProgressFraction - 2, height - 2);
    bar.setFill(fill);
    bar.setX(1);
    bar.setY(1);
    this.getChildren().add(bar);
  }

  void setFillWidth(boolean value) {
    if (value) {
      this.setMaxWidth(Double.MAX_VALUE);
    }
  }

  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    this.getChildren().clear();
    initializeBar(width, height);
  }

}