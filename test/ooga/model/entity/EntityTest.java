package ooga.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.model.ability.attacktypes.Attack;
import ooga.utility.event.CollisionEvent;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class EntityTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  private Entity buildEntity(String filename){
    EntityBuilder eb = new EntityBuilder();
    return eb.getEntity(filename);
  }

  private CollisionEvent buildCollisionEvent(String location, String attack, Entity entity){
    return new CollisionEvent(location, attack, entity);
  }

  @Test
  void testHandleCollisionHealth() {
    Entity mario = EntityBuilder.getEntity("Player");
    Entity brick = EntityBuilder.getEntity("Brick");
    Entity goomba = EntityBuilder.getEntity("Goomba");
    Entity ground = EntityBuilder.getEntity("Ground");
    Entity coin = EntityBuilder.getEntity("Coin");

    CollisionEvent sideDamage = new CollisionEvent("Side", "Damage", goomba);
    CollisionEvent topDamage = new CollisionEvent("Top", "Damage", goomba);
    CollisionEvent bottomDamage = new CollisionEvent("Bottom", "Damage", goomba);

    CollisionEvent sideBounce = new CollisionEvent("Side", "XBounce", brick);
    CollisionEvent topBounce = new CollisionEvent("Top", "YBounce", brick);
    CollisionEvent bottomBounce = new CollisionEvent("Bottom", "YBounce", brick);

    CollisionEvent sideSupport = new CollisionEvent("Side", "XSupport", ground);
    CollisionEvent topSupport = new CollisionEvent("Top", "YSupport", ground);
    CollisionEvent bottomSupport = new CollisionEvent("Bottom", "YSupport", ground);

    CollisionEvent sideCollectible = new CollisionEvent("Side", "Collectible", coin);
    CollisionEvent topCollectible = new CollisionEvent("Top", "Collectible", coin);
    CollisionEvent bottomCollectible = new CollisionEvent("Bottom", "Collectible", coin);

    mario.handleCollision(sideDamage);
    assertEquals(true, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(topDamage);
    assertEquals(true, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(bottomDamage);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(sideBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(topBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(bottomBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(sideSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(bottomSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(topSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(sideCollectible);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(bottomCollectible);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(topCollectible);
    assertEquals(false, mario.isDead());
  }

  @Test
  void testCollectionPoints(){
    Entity player = EntityBuilder.getEntity("Player");
    Entity coin = EntityBuilder.getEntity("Coin");

    CollisionEvent coinSideCE = new CollisionEvent("Side", coin.getAttack("Side"), coin);
    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);
    player.handleCollision(coinSideCE);
    coin.handleCollision(playerSideCE);
    assertEquals(100, player.getScore());
    assertEquals(0, coin.getScore());

    player = EntityBuilder.getEntity("Player");
    coin = EntityBuilder.getEntity("Coin");
    CollisionEvent coinTopCE = new CollisionEvent("Top", coin.getAttack("Bottom"), coin);
    CollisionEvent playerBottomCE = new CollisionEvent("Bottom", player.getAttack("Top"), player);
    player.handleCollision(coinTopCE);
    coin.handleCollision(playerBottomCE);
    assertEquals(100, player.getScore());
    assertEquals(0, coin.getScore());


    player = EntityBuilder.getEntity("Player");
    coin = EntityBuilder.getEntity("Coin");
    CollisionEvent coinBottomCE = new CollisionEvent("Bottom", coin.getAttack("Top"), coin);
    CollisionEvent playerTopCE = new CollisionEvent("Top", player.getAttack("Bottom"), player);
    player.handleCollision(coinBottomCE);
    coin.handleCollision(playerTopCE);
    assertEquals(100, player.getScore());
    assertEquals(0, coin.getScore());

    coin = EntityBuilder.getEntity("Coin");
    coinBottomCE = new CollisionEvent("Bottom", coin.getAttack("Top"), coin);
    player.handleCollision(coinBottomCE);
    coin.handleCollision(playerTopCE);
    assertEquals(200, player.getScore());
    assertEquals(0, coin.getScore());
  }

  @Test
  void testCollectionLife(){
    Entity player = EntityBuilder.getEntity("Player");
    Entity lifeMushroom = EntityBuilder.getEntity("LifeMushroom");
    CollisionEvent lifeMushroomSideCE = new CollisionEvent("Side", lifeMushroom.getAttack("Side"), lifeMushroom);
    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);

    player.handleCollision(lifeMushroomSideCE);
    lifeMushroom.handleCollision(playerSideCE);
    assertEquals(2, player.debugHealth());
    assertTrue(lifeMushroom.isDead());

    lifeMushroom = EntityBuilder.getEntity("LifeMushroom");
    CollisionEvent lifeMushroomTopCE = new CollisionEvent("Top", lifeMushroom.getAttack("Bottom"), lifeMushroom);
    CollisionEvent playerBottomCE = new CollisionEvent("Bottom", player.getAttack("Top"), player);
    player.handleCollision(lifeMushroomTopCE);
    lifeMushroom.handleCollision(playerBottomCE);
    assertEquals(3, player.debugHealth());
    assertTrue(lifeMushroom.isDead());

    lifeMushroom = EntityBuilder.getEntity("LifeMushroom");
    CollisionEvent lifeMushroomBottomCE = new CollisionEvent("Bottom", lifeMushroom.getAttack("Top"), lifeMushroom);
    CollisionEvent playerTopCE = new CollisionEvent("Top", player.getAttack("Bottom"), player);
    player.handleCollision(lifeMushroomBottomCE);
    lifeMushroom.handleCollision(playerTopCE);
    assertEquals(4, player.debugHealth());
    assertTrue(lifeMushroom.isDead());
  }

  @Test
  void testLevelEnd(){
    Entity player = EntityBuilder.getEntity("Player");
    Entity flag = EntityBuilder.getEntity("Flag");

    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);
    CollisionEvent flagSideCE = new CollisionEvent("Side", flag.getAttack("Side"), flag);

    player.handleCollision(flagSideCE);
    flag.handleCollision(playerSideCE);
    assertFalse(player.isDead());
    assertTrue(player.endedLevel());
    assertTrue(player.isSuccess());

  }
}