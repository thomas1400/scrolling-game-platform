package ooga.view.dynamicUI;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.assertj.core.util.VisibleForTesting;

/**
 * A custom-built UI control to display progress on a level map.
 *
 * Shows a bar that fills up a certain percentage of the pane, depending on
 * parameters levelProgress and totalLevels;
 *
 * To use in a layout, add the dynamicUI package and put an instance in the Screen's
 * dynamicNodes map. Layout in FXLR using the dynamicNodes tag.
 */
public class LevelProgressBar extends Pane {

  private static final int BAR_LAYOUT_OFFSET = 1;
  private double levelProgressFraction;
  private Region bar;
  private Label label;

  /**
   * Initialize a LevelProgressBar with overlaid text, level progress and total levels.
   *
   * @param text overlay text, e.g. "Progress"
   * @param levelProgress number of levels completed
   * @param totalLevels total number of levels
   */
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

  /**
   * Sets the preferred Pane width
   * @param width prefWidth
   */
  public void setPreferredWidth(double width) {
    this.setPrefWidth(width);
    bar.setPrefWidth(width * (float) levelProgressFraction - 2);
    label.setPrefWidth(width);
  }

  /**
   * Sets the preferred Pane height
   * @param height prefHeight
   */
  public void setPreferredHeight(double height) {
    this.setPrefHeight(height);
    bar.setPrefHeight(height - 2);
    label.setPrefHeight(height);
  }

  /**
   * Gets the width of the bar, only used for TESTING
   * @return bar prefWidth
   */
  @VisibleForTesting
  double getBarWidth() {
    return bar.getPrefWidth();
  }
}