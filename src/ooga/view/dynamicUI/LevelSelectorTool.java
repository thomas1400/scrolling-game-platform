package ooga.view.dynamicUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.BasicLevelList;
import ooga.exceptions.ExceptionFeedback;

public class LevelSelectorTool extends Pane {

  private static final double STROKE_WIDTH = 5.0;
  private static final double CENTER_OFFSET = 10.0;

  private BasicLevelList myLevels;
  private List<Integer> myUnlockedLevels;
  private List<Integer> myCompletedLevels;
  private ToggleGroup levelToggles;
  private boolean[][] adjacency;
  double[][] locations;
  private int numLevels;

  public LevelSelectorTool(BasicLevelList levels, String levelGraphFile, String levelMapFile,
      List<Integer> levelProgress) {

    myLevels = levels;
    myUnlockedLevels = new ArrayList<>();
    myCompletedLevels = levelProgress;

    parseGraph(levelGraphFile);
    parseMap(levelMapFile);

    findUnlockedLevels();

    drawLevelConnections();
    initializeLevelButtons(levelProgress);

    this.getStyleClass().add("level-selector-tool");
    this.setId("#tested");
  }

  private void initializeLevelButtons(List<Integer> levelProgress) {
    levelToggles = new ToggleGroup();
    for (int i : myUnlockedLevels) {
      RadioButton button = new RadioButton(myLevels.getBasicLevel(i+1).getMainTitle());
      button.setToggleGroup(levelToggles);
      button.setId(""+ (i + 1));
      if (levelProgress.size() > 0 && i == levelProgress.get(levelProgress.size()-1)) {
        button.setSelected(true);
      }
      button.setLayoutX(locations[i][0]);
      button.setLayoutY(locations[i][1]);
      this.getChildren().add(button);
    }
  }

  private void drawLevelConnections() {
    // draw connecting lines
    for (int a : myUnlockedLevels) {
      double[] start = locations[a];
      for (int b : myUnlockedLevels) {
        if (adjacency[a][b]) {
          double[] end = locations[b];
          Line connection = new Line(
              start[0] + CENTER_OFFSET, start[1] + CENTER_OFFSET,
              end[0] + CENTER_OFFSET, end[1] + CENTER_OFFSET);
          connection.setStrokeWidth(STROKE_WIDTH);
          connection.getStyleClass().add("level-connection");
          this.getChildren().add(connection);
        }
      }
    }
  }

  private void parseGraph(String file) {
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
      ExceptionFeedback.throwBreakingException(e, "Level graph not found.");
    }
  }

  private void parseMap(String file) {
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
      ExceptionFeedback.throwBreakingException(e, "Level map not found.");
    }
  }

  private void findUnlockedLevels() {
    // for each level in myLevels
    // only show the level if completedLevels contains a level that connects to it
    for (int i = 0; i < numLevels; i++) {
      int levelNum = i+1;
      if (myCompletedLevels.contains(i) || i == 0) {
        myUnlockedLevels.add(i);
      }
      for (int completed : myCompletedLevels) {
        if (completed < numLevels) {
          if (adjacency[completed][i]) {
            myUnlockedLevels.add(i);
          }
        }
      }
    }
  }

  public BasicLevel getSelected() {
    return myLevels.getBasicLevel(Integer.parseInt(((RadioButton) levelToggles.getSelectedToggle()).getId()));
  }

}