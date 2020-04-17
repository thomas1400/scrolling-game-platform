package ooga.controller;

public interface UpdateGame extends Communicable {
  void adjustPoints(int num);

  void handleWin();

  void adjustLives(int lifeAdjustment);

}
