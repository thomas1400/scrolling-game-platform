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

  private CollisionEvent buildCollisionEvent(String location, String attack, Entity entity){
    return new CollisionEvent(location, attack, entity);
  }

  @Test
  void testHandleCollision() {
    Entity mario = EntityBuilder.getEntity("Player");
    Entity brick = EntityBuilder.getEntity("Brick");
    Entity goomba = EntityBuilder.getEntity("Goomba");
    Entity ground = EntityBuilder.getEntity("Ground");
    //System.out.println("hello");
    CollisionEvent sideDamage = new CollisionEvent("SIDE", "Damage", goomba);
    CollisionEvent topDamage = new CollisionEvent("TOP", "Damage", goomba);
    CollisionEvent bottomDamage = new CollisionEvent("BOTTOM", "Damage", goomba);

    CollisionEvent sideBounce = new CollisionEvent("SIDE", "BounceX", brick);
    CollisionEvent topBounce = new CollisionEvent("TOP", "BounceY", brick);
    CollisionEvent bottomBounce = new CollisionEvent("BOTTOM", "BounceY", brick);

    CollisionEvent sideSupport = new CollisionEvent("SIDE", "SupportX", ground);
    CollisionEvent topSupport = new CollisionEvent("TOP", "SupportY", ground);
    CollisionEvent bottomSupport = new CollisionEvent("BOTTOM", "SupportY", ground);

    /*
    CollisionEvent sideStun = new CollisionEvent("SIDE", "Stun");
    CollisionEvent topStun = new CollisionEvent("TOP", "Stun");
    CollisionEvent bottomStun = new CollisionEvent("BOTTOM", "Stun");

    CollisionEvent sideHarmless = new CollisionEvent("SIDE", "Harmless");
    CollisionEvent topHarmless = new CollisionEvent("TOP", "Harmless");
    CollisionEvent bottomHarmless = new CollisionEvent("BOTTOM", "Harmless");
*/
    mario.handleCollision(sideDamage);
    assertEquals(true, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(topDamage);
    assertEquals(true, mario.isDead());

    mario = EntityBuilder.getEntity("Player");
    mario.handleCollision(bottomDamage);
    assertEquals(true, mario.isDead());

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

  }
}