package ooga.controller.levels;

import java.util.HashMap;
import java.util.Map;
import ooga.exceptions.ExceptionFeedback;

public class BasicLevelList {

  Map<Integer, BasicLevel> myLevels = new HashMap<>();
  String myGameType;

  /**
   * Adds a basic level to the List for future accessing
   * @param basicLevel the Basic level to be added
   */
  public void addBasicLevel(BasicLevel basicLevel){
    myLevels.put(basicLevel.getLevelIndex(), basicLevel);
    setListGameType(basicLevel);
  }

  private void setListGameType(BasicLevel basicLevel) {
    if (myGameType != null && !basicLevel.getGameType().equals(myGameType)){
      ExceptionFeedback.throwBreakingException(new RuntimeException(),
          "Invalid Level Type!\nThe level " + basicLevel.getMainTitle() + " is of type " + basicLevel.getGameType() +
              ". This BasicLevelList already has " + myGameType + " type levels in it.");
    }
    myGameType = basicLevel.getGameType();
  }

  /**
   * @return the number of levels currently stored in the BasicLevelList
   */
  public int size() {
    return myLevels.keySet().size();
  }

  /**
   * @param levelIndex number of the level to be accessed
   * @return The BasicLevel specified by the level index parameter
   */
  public BasicLevel getBasicLevel(int levelIndex){
    return myLevels.get(levelIndex);
  }

  /**
   * @return the gameType of the levels in the BasicLevelList. They are all required to have the
   * same game type so there is only one value per a BasicLevelList.
   */
  public String getGameType(){
    return myGameType;
  }

}