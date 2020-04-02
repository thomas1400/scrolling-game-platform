package ooga.engine.loop;

import ooga.model.entity.EntityList;

public interface Loopable {

  void beginLoop();

  void endLoop();

  EntityList getNextState();

}
