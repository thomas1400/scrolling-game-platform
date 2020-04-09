package ooga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import ooga.controller.LevelBuilder;
import ooga.controller.LevelController;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.view.GameScreen;

public class Tester {

  public static void main(String[] args) throws FileNotFoundException, ExceptionFeedback {
    LevelBuilder.buildLevel("0-1");
  }
}
