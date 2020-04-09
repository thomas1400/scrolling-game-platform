package ooga.view;

import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class LevelSelectorScreen extends Screen {

  public LevelSelectorScreen(ScreenController controller) {
    super(controller);
    setWorkingDimensions(3, 1);
    initializeLayout();
  }

  public void initializeLayout() {
    ControlFactory cf = new ControlFactory(PADDING);
    VBox layout = new VBox();
    layout.setAlignment(Pos.TOP_CENTER);

    Label user = cf.label("User X", BUTTON_FONT_SIZE);
    user.setPrefHeight(workingHeight * 0.1);
    layout.getChildren().add(user);

    LevelSelectorTool lst = new LevelSelectorTool(workingWidth, workingHeight*0.8, "", 0);
    cf.setMargin(lst);
    layout.getChildren().add(lst);

    HBox menu = new HBox();
    menu.setAlignment(Pos.CENTER);
    menu.setPadding(new Insets(5));
    menu.setSpacing(5);
    menu.setPrefHeight(workingHeight * 0.1);

    Button back = cf.button(resources.getString("back"), BUTTON_FONT_SIZE,
        e->handleButtonPress("back") , 100, menu.getPrefHeight());
    menu.getChildren().add(back);

    Label progress = cf.label(resources.getString("progress"), BUTTON_FONT_SIZE);
    menu.getChildren().add(progress);

    LevelProgressBar lpb = new LevelProgressBar(200, menu.getPrefHeight(), 1, 3);
    menu.getChildren().add(lpb);

    Button start = cf.button(resources.getString("begin"), BUTTON_FONT_SIZE,
        e-> handleButtonPress("begin"), 100, menu.getPrefHeight());
    menu.getChildren().add(start);

    layout.getChildren().add(menu);

    this.getChildren().add(layout);
  }

  private class LevelSelectorTool extends Pane {

    private ToggleGroup levels;
    private final int NUM_LEVELS = 3;
    private Map<Integer, List<Integer>> connections;

    LevelSelectorTool(double width, double height, String levelMapFilePath, int levelProgress) {

      this.setPrefSize(width, height);
      this.setMinSize(width, height);
      this.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

      levels = new ToggleGroup();
      for (int i = 0; i < NUM_LEVELS; i++) {
        RadioButton button = new RadioButton("1-" + i);
        button.setToggleGroup(levels);
        if (i == 0) {
          button.setSelected(true);
        }
        button.setLayoutX(i*75 + 10);
        button.setLayoutY(i*75 + 10);
        this.getChildren().add(button);
      }
    }

    String getSelected() {
      String info = levels.getSelectedToggle().toString();
      return info.substring(info.indexOf('\'') + 1, info.lastIndexOf('\''));
    }
  }

  private class LevelProgressBar extends Pane {

    private final Color background = Color.WHITE;
    private final Color fill = Color.DARKGREY;
    private final Color border = Color.BLACK;
    private double levelProgressFraction;

    LevelProgressBar(double width, double height, int levelProgress, int totalLevels) {
      this.setPrefSize(width, height);
      this.setMaxSize(width, height);
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

}
