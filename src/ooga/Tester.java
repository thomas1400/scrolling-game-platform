package ooga;

import java.io.File;
import java.io.FileNotFoundException;
import ooga.controller.UserFactory;

public class Tester {

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println(UserFactory.getUser(new File("resources/users/Cayla.user")));
  }
}
