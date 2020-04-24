package ooga.view.dynamicUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;
import ooga.view.screen.LevelSelectorScreen;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LevelProgressBarTest extends ApplicationTest {

  private LevelProgressBar lpb;
  private ScreenController sc;

  @Override
  public void start(Stage primaryStage) throws Exception {
    super.start(primaryStage);
    sc = new ScreenController(primaryStage, new UserList());
    primaryStage.hide();
  }

  @Test
  void testBarLength1() {

    BasicLevelList bll = new BasicLevelList();
    Map<String, String> headerInfo = new HashMap<>();
    headerInfo.put("gameType", "mario");
    headerInfo.put("mainTitle", "title");
    headerInfo.put("subTitle", "subtitle");
    headerInfo.put("backgroundImage", "null");
    bll.addBasicLevel(new BasicLevel(1, null, headerInfo));
    bll.addBasicLevel(new BasicLevel(2, null, headerInfo));
    bll.addBasicLevel(new BasicLevel(3, null, headerInfo));

    LevelSelectorScreen lss = new LevelSelectorScreen(sc, bll);
    lpb = (LevelProgressBar) lss.getDynamicUIElement("level-progress-bar");

    double fractionFilled = lpb.getBarWidth() / lpb.getPrefWidth();
    assertTrue(Math.abs(fractionFilled - 0.333) <= 0.01);
  }

  @Test
  void testBarLength2() {

    BasicLevelList bll = new BasicLevelList();
    Map<String, String> headerInfo = new HashMap<>();
    headerInfo.put("gameType", "mario");
    headerInfo.put("mainTitle", "title");
    headerInfo.put("subTitle", "subtitle");
    headerInfo.put("backgroundImage", "null");
    bll.addBasicLevel(new BasicLevel(1, null, headerInfo));
    bll.addBasicLevel(new BasicLevel(2, null, headerInfo));

    LevelSelectorScreen lss = new LevelSelectorScreen(sc, bll);
    lpb = (LevelProgressBar) lss.getDynamicUIElement("level-progress-bar");

    double fractionFilled = lpb.getBarWidth() / lpb.getPrefWidth();
    assertTrue(Math.abs(fractionFilled - 0.5) <= 0.01);
  }

}
