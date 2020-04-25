package ooga.model.ability;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
//import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class HealthTest extends ApplicationTest { //so that error throwing can be seen

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  private Health immortal(){
    return new Health();
  }

  private Health weak(){
    return new Health("Weak");
  }

  private Health average(){
    return new Health("Average");
  }

  private Health strong(){
    return new Health("Strong");
  }

  private Health badHealth(){
    return new Health("sfhkj");
  }

  @Test
  void createImmortalHealth(){
    Health h = immortal();
    assertFalse(h.isDead());
    h.damage();
    assertFalse(h.isDead());
  }

  @Test
  void createWeakHealth(){
    Health h = weak();
    assertEquals(1, h.getLives());
    assertFalse(h.isDead());
    h.damage();
    assertTrue(h.isDead());
  }

  @Test
  void createAverageHealth(){
    Health h = average();
    assertFalse(h.isDead());
    assertEquals(2, h.getLives());
    h.damage();
    assertFalse(h.isDead());
    assertEquals(1, h.getLives());
    h.damage();
    assertTrue(h.isDead());
  }

  @Test
  void createStrongHealth(){
    Health h = strong();
    assertFalse(h.isDead());
    assertEquals(3, h.getLives());
    h.damage();
    assertFalse(h.isDead());
    assertEquals(2, h.getLives());
    h.damage();
    assertFalse(h.isDead());
    assertEquals(1, h.getLives());
    h.damage();
    assertTrue(h.isDead());
  }

  @Test
  void isDead() {
    Health h = weak();
    assertFalse(h.isDead());
    h.damage();
    assertEquals(0, h.getLives());
    assertTrue(h.isDead());

    h = immortal();
    assertEquals(0, h.getLives());
    assertFalse(h.isDead());
  }

  @Test
  void addLives() {
    Health h = weak();
    assertEquals(1, h.getLives());
    h.addLives(10);
    assertEquals(11, h.getLives());

    h = weak();
    assertEquals(1, h.getLives());
    h.addLives(-11);
    assertEquals(-10, h.getLives());
  }

  @Test
  void setLives() {
    Health h = weak();
    assertEquals(1, h.getLives());
    h.setLives(10);
    assertEquals(10, h.getLives());
  }

  @Test
  void damage() {
    Health h = average();
    assertEquals(2, h.getLives());
    h.damage();
    assertEquals(1, h.getLives());

    h = weak();
    assertEquals(1, h.getLives());
    h.damage();
    assertEquals(0, h.getLives());
    assertTrue(h.isDead());
    h.damage();
    assertTrue(h.isDead());
    assertEquals(0, h.getLives());
  }

  @Test
  void getLives() {
    Health h = weak();
    assertEquals(1, h.getLives());

    h = average();
    assertEquals(2, h.getLives());


    h = strong();
    assertEquals(3, h.getLives());

    h = immortal();
    assertEquals(0, h.getLives());
  }

  @Test
  void checkVitality(){
    Health h = badHealth();
  }
}