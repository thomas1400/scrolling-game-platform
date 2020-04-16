package ooga.controller.data;

import java.util.HashMap;
import java.util.Map;

public class BasicLevelList {

  Map<Integer, BasicLevel> myLevels = new HashMap<>();

  public void addBasicLevel(BasicLevel basicLevel){
    myLevels.put(basicLevel.getLevelIndex(), basicLevel);
  }

  public void addAllBasicLevels(Iterable<BasicLevel> basicLevels){
    for (BasicLevel basicLevel : basicLevels) {
      myLevels.put(basicLevel.getLevelIndex(), basicLevel);
    }
  }

  public int size() {
    return myLevels.keySet().size();
  }



  public BasicLevel getBasicLevel(int levelIndex){
    return myLevels.get(levelIndex);
  }

}