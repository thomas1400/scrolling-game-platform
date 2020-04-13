package ooga;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import ooga.controller.UserSaver;
import ooga.controller.data.User;

public class Tester {

  public static void main(String[] args) throws FileNotFoundException {
    ArrayList<Integer> unlocked = new ArrayList<>();
    unlocked.add(7);
    UserSaver.saveUser(new User("Thank You", "thankyou.png", unlocked, 56));
  }
}
