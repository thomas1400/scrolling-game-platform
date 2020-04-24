package ooga.controller.levels;

import java.io.File;
import java.util.Map;

public class BasicLevel {

  public static final String GAME_PACKAGE = "gamedata/";
  public static final String LEVELS_BACKGROUND_PACKAGE = "/levels/backgrounds/";

  public static final String GAME_TYPE = "gameType";
  public static final String MAIN_TITLE = "mainTitle";
  public static final String SUB_TITLE = "subTitle";
  public static final String BACKGROUND_IMAGE = "backgroundImage";

  private int myLevelIndex;
  private File myLevelFile;
  private String myGameType;
  private String myMainTitle;
  private String mySubTitle;
  private String myBackgroundImage;
  private Map<String, String> myHeaderInfo;

  /**
   * Simply a package for all relevant Level data. Generated in the start of the ScreenController
   * class (once a gameType is chosen)and passed to the Screens and eventually the LevelController
   * to retrieve all relevant information.
   *
   * @param index The Level's Number
   * @param levelFile The Level File to be Parsed Later
   * @param headerInfo Important Level Info, the Basic parts of which are separated out into
   *                   BasicLevel instance variables
   */
  public BasicLevel(int index, File levelFile, Map<String,String> headerInfo){
    myLevelIndex = index;
    myLevelFile = levelFile;
    myHeaderInfo = headerInfo;
    myGameType = myHeaderInfo.get(GAME_TYPE);
    myMainTitle = myHeaderInfo.get(MAIN_TITLE);
    mySubTitle = myHeaderInfo.get(SUB_TITLE);
    myBackgroundImage = GAME_PACKAGE + myGameType + LEVELS_BACKGROUND_PACKAGE + myHeaderInfo.get(
        BACKGROUND_IMAGE);
  }

  /**
   * @return The Level's integer Identifier (determines order)
   */
  public int getLevelIndex() {
    return myLevelIndex;
  }

  /**
   * @return The Level's Main Title to be displayed on Screens
   */
  public String getMainTitle(){
    return myMainTitle;
  }

  /**
   * @return The Level's Sub Title to be displayed on Screens
   */
  public String getSubTitle() {
    return mySubTitle;
  }

  /**
   * @return The Level's Background Image to be displayed on the GameScreen
   */
  public String getBackgroundImage() {
    return myBackgroundImage;
  }

  /**
   * @return More complicated, un-parsed, information about the level needed to create a
   * CompleteLevel with pertinent details
   */
  public Map<String, String> getHeaderInfo() {
    return myHeaderInfo;
  }

  /**
   * @return The Level's Actual File for Level Creation Parsing when being turned into a
   * CompleteLevel
   */
  public File getLevelFile() { return myLevelFile; }

  /**
   * @return The Level's Game Type for resource path accessing by classes dealing with the level
   */
  public String getGameType() {
    return myGameType;
  }
}
