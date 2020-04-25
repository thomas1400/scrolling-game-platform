package ooga.model.behavior;


public interface Collidable {


  boolean isDead();

  double getData(String informationType);

  void otherCollectMe();

  void size(Double value);

  String getAttack(String location);

  void otherResetAfterCollect();

}
