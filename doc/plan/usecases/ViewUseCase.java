import ooga.controller.ScreenController;

public class ViewUseCase {

  /**
    * This code can be run inside the UserSelectorScreen class,
    * assuming Ooga Application instance has been created, and sc is the Ooga application's
    * ScreenController, which is held by each Screen.
  */
  public static void main(String[] args) {
    ScreenController sc = new ScreenController();
    try {
      sc.handleButtonPress("userselect1");
      sc.switchToScreen("main");
    } catch (Exception e) {
      ExceptionFeedback.throw(e,
        "Failed to load user profile. Close the application and try again."
      );
    }
  }
}