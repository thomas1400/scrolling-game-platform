package ooga.controller;

public interface GameLevel extends Communicable {
  void handleWin();
  void adjustPoints(int num);
  void adjustLives(int num);

}
