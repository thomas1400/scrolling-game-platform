package ooga.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.ability.attacktypes.Attack;
import ooga.utility.event.CollisionEvent;
import org.junit.jupiter.api.Test;

class EntityTest {

  private Entity buildEntity(String filename){
    EntityBuilder eb = new EntityBuilder();
    return eb.getEntity(filename);
  }

  private CollisionEvent buildCollisionEvent(String location, Attack attack){
    return new CollisionEvent(location, attack);
  }

  @Test
  void testUpdateAttack() {

  }

  @Test
  void testAddAbility() {
  }

  @Test
  void testHandleCollision() {
    Entity mario = buildEntity("Mario.png");
    CollisionEvent sideDamage = buildCollisionEvent("SIDE", Attack.DAMAGE);
    CollisionEvent topDamage = buildCollisionEvent("TOP", Attack.DAMAGE);
    CollisionEvent bottomDamage = buildCollisionEvent("BOTTOM", Attack.DAMAGE);

    CollisionEvent sideBounce = buildCollisionEvent("SIDE", Attack.BOUNCE);
    CollisionEvent topBounce = buildCollisionEvent("TOP", Attack.BOUNCE);
    CollisionEvent bottomBounce = buildCollisionEvent("BOTTOM", Attack.BOUNCE);

    CollisionEvent sideSupport = buildCollisionEvent("SIDE", Attack.SUPPORT);
    CollisionEvent topSupport = buildCollisionEvent("TOP", Attack.SUPPORT);
    CollisionEvent bottomSupport = buildCollisionEvent("BOTTOM", Attack.SUPPORT);

    CollisionEvent sideStun = buildCollisionEvent("SIDE", Attack.STUN);
    CollisionEvent topStun = buildCollisionEvent("TOP", Attack.STUN);
    CollisionEvent bottomStun = buildCollisionEvent("BOTTOM", Attack.STUN);

    CollisionEvent sideHarmless = buildCollisionEvent("SIDE", Attack.HARMLESS);
    CollisionEvent topHarmless = buildCollisionEvent("TOP", Attack.HARMLESS);
    CollisionEvent bottomHarmless = buildCollisionEvent("BOTTOM", Attack.HARMLESS);

    assertEquals(/*what do I put in here?*/1, 1);
  }

  @Test
  void testUpdateVisualization() {
  }

  @Test
  void testMoveRight() {

  }

  @Test
  void testMoveLeft() {
  }

  @Test
  void testJump() {
  }
}