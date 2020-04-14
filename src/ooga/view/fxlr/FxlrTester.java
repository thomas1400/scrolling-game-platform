package ooga.view.fxlr;

import java.io.File;

public class FxlrTester {

  public static void main(String[] args) {
    try {
      FXLRParser parser = new FXLRParser();
      parser.parse(new File("resources/view/HomeScreen.fxlr"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
