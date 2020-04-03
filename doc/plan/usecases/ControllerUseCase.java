import ooga.controller.LevelController;

public class ControllerUseCase {

  /**
   * This would be code contained and called within the Level Controller class
   * This use case details how a new level would be parsed and created, and the game begun.
   */
  public static void main(String[] args) {

    String levelFilePath = "myExampleLevel.level";
    Level myLevel = parseLevel(levelFilePath);
    LevelLoop myLoop = new LevelLoop(myLevel);

    myGameScreen.addAll(myLevel.getEntityList());

    myLoop.begin();

    }
  }

  /**
   * Called by the LevelController
   * Contained within the LevelParser class
   *
   * @param filePath sent by LevelController
   * @return a Level Object
   */
  public static Level parseLevel(String filePath) {

    Level myLevel = new Level();

    File levelFile = new File(filePath);

    Scanner sc = new Scanner(levelFile);

    while(sc.hasNextLine()){
      String line = scnr.nextLine();
      myLevel.add(parseLine(line));
    }

  }
}