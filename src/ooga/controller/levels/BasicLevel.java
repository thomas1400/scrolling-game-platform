package ooga.controller.levels;

import java.io.File;
import java.util.Map;

/**
 * Simply a package for all relevant Level data. Generated within the LevelBuilder class and
 * passed to the LevelLoop to retrieve all relevant information.
 *
 * Could theoretically be used by a "LevelSaver" class to turn back into a file
 */
public class BasicLevel {

  private int myLevelIndex;
  private File myLevelFile;
  private String myGameType;
  private String myMainTitle;
  private String mySubTitle;
  private String myBackgroundImage;
  private Map<String, String> myHeaderInfo;

  public BasicLevel(int index, File levelFile, Map<String,String> headerInfo){
    myLevelIndex = index;
    myLevelFile = levelFile;
    myHeaderInfo = headerInfo;
    myGameType = myHeaderInfo.get("gameType");
    myMainTitle = myHeaderInfo.get("mainTitle");
    mySubTitle = myHeaderInfo.get("subTitle");
    myBackgroundImage = myHeaderInfo.get("backgroundImage");
  }

  public int getLevelIndex() {
    return myLevelIndex;
  }

  public String getMainTitle(){
    return myMainTitle;
  }

  public String getSubTitle() {
    return mySubTitle;
  }

  public String getBackgroundImage() {
    return myBackgroundImage;
  }

  public Map<String, String> getHeaderInfo() {
    return myHeaderInfo;
  }

  public File getLevelFile() { return myLevelFile; }

  public String getGameType() {
    return myGameType;
  }
}
