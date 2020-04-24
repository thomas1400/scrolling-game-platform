package ooga.view.dynamicUI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;
import ooga.view.screen.LevelSelectorScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LevelSelectorTest extends ApplicationTest {

  private LevelSelectorTool lst;
  private BasicLevelList bll;

  @Override
  public void start(Stage primaryStage) throws Exception {
    super.start(primaryStage);
    ScreenController sc = new ScreenController(new Stage(), new UserList());

    bll = new BasicLevelList();
    Map<String, String> headerInfo = new HashMap<>();
    headerInfo.put("gameType", "mario");
    headerInfo.put("mainTitle", "title");
    headerInfo.put("subTitle", "subtitle");
    headerInfo.put("backgroundImage", "null");
    bll.addBasicLevel(new BasicLevel(1, null, headerInfo));
    bll.addBasicLevel(new BasicLevel(2, null, headerInfo));
    bll.addBasicLevel(new BasicLevel(3, null, headerInfo));

    LevelSelectorScreen lss = new LevelSelectorScreen(sc, bll);
    lst = (LevelSelectorTool) lss.getDynamicUIElement("level-selector-tool");

    primaryStage.setScene(new Scene(lss));
    primaryStage.setX(0);
    primaryStage.setY(0);
    primaryStage.show();
  }

  @Test
  void autoSelectFurthest() {
    assertEquals(bll.getBasicLevel(1), lst.getSelected());
  }

  @Test
  void selectUnlockedLevels() {
    assertEquals(bll.getBasicLevel(1), lst.getSelected());
    clickOn(100, 180);
    assertEquals(bll.getBasicLevel(2), lst.getSelected());
  }

  @Test
  void selectLockedLevels() {
    assertEquals(bll.getBasicLevel(1), lst.getSelected());
    clickOn(170, 220);
    assertNotEquals(bll.getBasicLevel(3), lst.getSelected());
  }

}
