package ooga.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class EntityBuilderTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @Test
  void getEntityCorrectFile() {
   //assertDoesNotThrow(() => EntityBuilder.getEntity("Player", "mario"));
    assertDoesNotThrow(()->{
      EntityBuilder.getEntity("Player", "mario");
    });

  }

  @Test
  void getEntityBadFileAttack(){
    assertThrows(RuntimeException.class, () -> {
      EntityBuilder.getEntity("BADPlayerAttack", "mario");
    });
  }

  @Test
  void getEntityBadFileAbility(){
    assertThrows(RuntimeException.class, () -> {
      EntityBuilder.getEntity("BADPlayerAbility", "mario");
    });
  }

  @Test
  void getEntityBadFileVitality(){
    assertThrows(RuntimeException.class, () -> {
      EntityBuilder.getEntity("BADPlayerVitality", "mario");
    });
  }

  @Test
  void getEntityWrongFileName(){
    assertThrows(RuntimeException.class, () -> {
      EntityBuilder.getEntity("hdjsfjsjhs", "mario");
    });
  }
}