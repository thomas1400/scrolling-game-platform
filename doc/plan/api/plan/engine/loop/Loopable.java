package plan.engine.loop;

import plan.model.entity.EntityList;

public interface Loopable {

  void beginLoop();

  void endLoop();

  EntityList getNextState();

}
