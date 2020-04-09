package ooga.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.factory.ControlFactory;

public class LevelSelectorScreen extends Screen {

  private static final String LEVEL_GRAPH_FILE = "resources/levels/LevelGraph.txt";
  private static final String LEVEL_MAP_FILE = "resources/levels/LevelMap.txt";

  private LevelSelectorTool lst;

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

    lst = new LevelSelectorTool(workingWidth, workingHeight*0.8,
        LEVEL_GRAPH_FILE, LEVEL_MAP_FILE, 0);
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

  public void loadLevel() {
    controller.initializeNewLevel(lst.getSelected());
  }

  private class LevelSelectorTool extends Pane {

    private static final double STROKE_WIDTH = 5.0;
    private static final double CENTER_OFFSET = 5.0;

    private ToggleGroup levels;
    private boolean[][] adjacency;
    double[][] locations;
    private int numLevels;

    LevelSelectorTool(double width, double height, String levelGraphFile, String levelMapFile, int levelProgress) {
      parseGraph(levelGraphFile);
      parseMap(levelMapFile);

      this.setPrefSize(width, height);
      this.setMinSize(width, height);
      this.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

      // draw connecting lines
      for (int a = 0; a < numLevels; a++) {
        double[] start = locations[a];
        for (int b = 0; b < numLevels; b++) {
          if (adjacency[a][b]) {
            double[] end = locations[b];
            Line connection = new Line(
                start[0] + CENTER_OFFSET, start[1] + CENTER_OFFSET,
                end[0] + CENTER_OFFSET, end[1] + CENTER_OFFSET);
            connection.setStrokeWidth(STROKE_WIDTH);
            this.getChildren().add(connection);
          }
        }
      }

      levels = new ToggleGroup();
      for (int i = 0; i < numLevels; i++) {
        RadioButton button = new RadioButton("1-" + i);
        button.setFont(Font.font(FONT_FAMILY, DETAIL_FONT_SIZE));
        button.setToggleGroup(levels);
        button.setId(Integer.toString(i+1));
        if (i == 0) {
          button.setSelected(true);
        }
        button.setLayoutX(locations[i][0]);
        button.setLayoutY(locations[i][1]);
        this.getChildren().add(button);
      }
    }

    void parseGraph(String file) {
      try {
        Scanner s = new Scanner(new File(file));

        int numLevels = Integer.parseInt(s.nextLine());
        this.numLevels = numLevels;

        adjacency = new boolean[numLevels][numLevels];

        while (s.hasNextLine()) {
          String[] edge = s.nextLine().split("\\s+");
          int v0 = Integer.parseInt(edge[0]) - 1, v1 = Integer.parseInt(edge[1]) - 1;
          adjacency[v0][v1] = true;
          adjacency[v1][v0] = true;
        }

        s.close();
      } catch (FileNotFoundException e) {
        ExceptionFeedback.throwException(e, "Level graph not found.");
      }
    }

    void parseMap(String file) {
      try {
        Scanner s = new Scanner(new File(file));

        //skip the header
        String firstLine = s.nextLine();
        while (firstLine.charAt(0) == '#') {
          firstLine = s.nextLine();
        }

        int numLevels = Integer.parseInt(firstLine);
        locations = new double[numLevels][];

        while (s.hasNextLine()) {
          int levelNum = s.nextInt() - 1;
          double[] location = new double[]{s.nextInt(), s.nextInt()};
          locations[levelNum] = location;
        }

        s.close();
      } catch (FileNotFoundException e) {
        ExceptionFeedback.throwException(e, "Level map not found.");
      }
    }

    int getSelected() {
      return Integer.parseInt(((RadioButton) levels.getSelectedToggle()).getId());
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
