package ooga.model.data;

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
  private String myMainTitle;
  private String mySubTitle;
  private Map<String, String> myHeaderInfo;

  public BasicLevel(int index, File levelFile, Map<String,String> headerInfo){
    myLevelIndex = index;
    myLevelFile = levelFile;
    myHeaderInfo = headerInfo;
    myMainTitle = myHeaderInfo.get("mainTitle");
    mySubTitle = myHeaderInfo.get("subTitle");
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

  public Map<String, String> getHeaderInfo() {
    return myHeaderInfo;
  }

  public File getLevelFile() { return myLevelFile; }
}
