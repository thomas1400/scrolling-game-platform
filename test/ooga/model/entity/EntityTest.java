package ooga.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.utility.event.CollisionEvent;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class EntityTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  private CollisionEvent buildCollisionEvent(String location, String otherLocation, Entity entity){
    return new CollisionEvent(location, entity.getAttack(otherLocation), entity);
  }

  @Test
  void testHandleCollisionHealth() {
    Entity mario = EntityBuilder.getEntity("Player", "mario");
    Entity brick = EntityBuilder.getEntity("Brick", "mario");
    Entity goomba = EntityBuilder.getEntity("Goomba", "mario");
    Entity ground = EntityBuilder.getEntity("Ground", "mario");
    Entity coin = EntityBuilder.getEntity("Coin", "mario");

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

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(topDamage);
    assertEquals(true, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(bottomDamage);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(sideBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(topBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(bottomBounce);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(sideSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(bottomSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(topSupport);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(sideCollectible);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(bottomCollectible);
    assertEquals(false, mario.isDead());

    mario = EntityBuilder.getEntity("Player", "mario");
    mario.handleCollision(topCollectible);
    assertEquals(false, mario.isDead());
  }

  @Test
  void testCollectionPoints(){
    Entity player = EntityBuilder.getEntity("Player", "mario");
    Entity coin = EntityBuilder.getEntity("Coin", "mario");

    CollisionEvent coinSideCE = new CollisionEvent("Side", coin.getAttack("Side"), coin);
    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);
    player.handleCollision(coinSideCE);
    coin.handleCollision(playerSideCE);
    assertEquals(10, player.getScore());
    assertEquals(0, coin.getScore());

    player = EntityBuilder.getEntity("Player", "mario");
    coin = EntityBuilder.getEntity("Coin", "mario");
    CollisionEvent coinTopCE = new CollisionEvent("Top", coin.getAttack("Bottom"), coin);
    CollisionEvent playerBottomCE = new CollisionEvent("Bottom", player.getAttack("Top"), player);
    player.handleCollision(coinTopCE);
    coin.handleCollision(playerBottomCE);
    assertEquals(10, player.getScore());
    assertEquals(0, coin.getScore());


    player = EntityBuilder.getEntity("Player", "mario");
    coin = EntityBuilder.getEntity("Coin", "mario");
    CollisionEvent coinBottomCE = new CollisionEvent("Bottom", coin.getAttack("Top"), coin);
    CollisionEvent playerTopCE = new CollisionEvent("Top", player.getAttack("Bottom"), player);
    player.handleCollision(coinBottomCE);
    coin.handleCollision(playerTopCE);
    assertEquals(10, player.getScore());
    assertEquals(0, coin.getScore());
  }

  @Test
  void testCollectionLife(){
    Entity player = EntityBuilder.getEntity("Player", "mario");
    Entity lifeMushroom = EntityBuilder.getEntity("LifeMushroom", "mario");
    CollisionEvent lifeMushroomSideCE = new CollisionEvent("Side", lifeMushroom.getAttack("Side"), lifeMushroom);
    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);

    player.handleCollision(lifeMushroomSideCE);
    lifeMushroom.handleCollision(playerSideCE);
    assertEquals(2, player.getLives());
    assertTrue(lifeMushroom.isDead());

    lifeMushroom = EntityBuilder.getEntity("LifeMushroom", "mario");
    CollisionEvent lifeMushroomTopCE = new CollisionEvent("Top", lifeMushroom.getAttack("Bottom"), lifeMushroom);
    CollisionEvent playerBottomCE = new CollisionEvent("Bottom", player.getAttack("Top"), player);
    player.handleCollision(lifeMushroomTopCE);
    lifeMushroom.handleCollision(playerBottomCE);
    assertEquals(3, player.getLives());
    assertTrue(lifeMushroom.isDead());

    lifeMushroom = EntityBuilder.getEntity("LifeMushroom", "mario");
    CollisionEvent lifeMushroomBottomCE = new CollisionEvent("Bottom", lifeMushroom.getAttack("Top"), lifeMushroom);
    CollisionEvent playerTopCE = new CollisionEvent("Top", player.getAttack("Bottom"), player);
    player.handleCollision(lifeMushroomBottomCE);
    lifeMushroom.handleCollision(playerTopCE);
    assertEquals(4, player.getLives());
    assertTrue(lifeMushroom.isDead());
  }

  @Test
  void testLevelEndAndSuccess(){
    Entity player = EntityBuilder.getEntity("Player", "mario");
    Entity flag = EntityBuilder.getEntity("Flag", "mario");

    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);
    CollisionEvent flagSideCE = new CollisionEvent("Side", flag.getAttack("Side"), flag);

    player.handleCollision(flagSideCE);
    flag.handleCollision(playerSideCE);
    assertFalse(player.isDead());
    assertTrue(player.endedLevel());

    player = EntityBuilder.getEntity("Player", "mario");
    Entity goomba = EntityBuilder.getEntity("Goomba", "mario");

    CollisionEvent sideDamage = new CollisionEvent("Side", "Damage", goomba);

    player.handleCollision(sideDamage);
    assertTrue(player.isDead());
    player.updateVisualization();
    assertTrue(player.endedLevel());
  }

  @Test
  void testHandleCollisionBounce(){
    Entity player = EntityBuilder.getEntity("Player", "mario");
    Entity goomba = EntityBuilder.getEntity("Goomba", "mario");
    Entity brick = EntityBuilder.getEntity("Brick", "mario");
    Entity coin = EntityBuilder.getEntity("Coin", "mario");

    CollisionEvent brickSideCE = new CollisionEvent("Side", brick.getAttack("Side"), brick);
    CollisionEvent brickTopCE = new CollisionEvent("Top", brick.getAttack("Bottom"), brick);
    CollisionEvent brickBottomCE = new CollisionEvent("Bottom", brick.getAttack("Top"), brick);

    CollisionEvent playerSideCE = new CollisionEvent("Side", player.getAttack("Side"), player);
    CollisionEvent playerTopCE = new CollisionEvent("Top", player.getAttack("Bottom"), player);
    CollisionEvent playerBottomCE = new CollisionEvent("Bottom", player.getAttack("Top"), player);

    CollisionEvent goombaSideCE = new CollisionEvent("Side", goomba.getAttack("Side"), goomba);
    CollisionEvent goombaTopCE = new CollisionEvent("Top", goomba.getAttack("Bottom"), goomba);
    CollisionEvent goombaBottomCE = new CollisionEvent("Bottom", goomba.getAttack("Top"), goomba);

    CollisionEvent coinSideCE = new CollisionEvent("Side", coin.getAttack("Side"), coin);
    CollisionEvent coinTopCE = new CollisionEvent("Top", coin.getAttack("Bottom"), coin);
    CollisionEvent coinBottomCE = new CollisionEvent("Bottom", coin.getAttack("Top"), coin);

    player.handleCollision(brickTopCE);

  }
}
