package ooga.controller.levels;

import java.util.HashMap;
import java.util.Map;
import ooga.exceptions.ExceptionFeedback;

public class BasicLevelList {

  Map<Integer, BasicLevel> myLevels = new HashMap<>();
  String myGameType;

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

  public int size() {
    return myLevels.keySet().size();
  }

  public BasicLevel getBasicLevel(int levelIndex){
    return myLevels.get(levelIndex);
  }

  public String getGameType(){
    return myGameType;
  }

}