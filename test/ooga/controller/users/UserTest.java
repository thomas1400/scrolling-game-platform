package ooga.controller.users;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserTest {


  @Test
  void unlockNextLevel() {
    User myUser = UserFactory.getDefaultUser();

    List<Integer> unlockedLevels = new ArrayList<>();
    unlockedLevels.add(0);

    myUser.unlockNextLevel("mario", 4);
    unlockedLevels.add(4);
    assertTrue(myUser.getLevelsCompleted("mario").containsAll(unlockedLevels));
    assertFalse(myUser.getLevelsCompleted("dinorun").containsAll(unlockedLevels));

    myUser.unlockNextLevel("mario", 7);
    unlockedLevels.add(7);
    assertTrue(myUser.getLevelsCompleted("mario").containsAll(unlockedLevels));
    assertFalse(myUser.getLevelsCompleted("flappy").containsAll(unlockedLevels));
  }

  @Test
  void adjustPoints() {
    User myUser = UserFactory.getDefaultUser();

    myUser.adjustPoints(57);
    assertEquals(myUser.getPoints(), 57);
    assertEquals(myUser.getPointsProperty().getValue(), 57);

    myUser.adjustPoints(-32);
    assertEquals(myUser.getPoints(), 57-32);
    assertEquals(myUser.getPointsProperty().getValue(), 57-32);
  }

  @Test
  void canConvertPointsToLife() {
    User myUser = UserFactory.getDefaultUser();

    myUser.adjustPoints(57);
    assertFalse(myUser.canConvertPointsToLife());

    myUser.adjustPoints(76);
    assertTrue(myUser.canConvertPointsToLife());
  }
}