package ooga.view.screen;

import ooga.controller.ScreenController;
import ooga.controller.users.UserList;
import ooga.view.dynamicUI.UserSelector;

/**
 * Allows the user to select a profile from a list of options, or create a new one.
 */
public class UserSelectorScreen extends Screen {

  private UserSelector us;

  public UserSelectorScreen(ScreenController controller, UserList users) {
    super(controller);
    us = new UserSelector(controller.getUsers());

    dynamicNodes.put("user-selector", us);

    loadLayout();
  }

  /**
   * Passes the selected user to the ScreenController. Called via REFLECTION.
   */
  public void selectUser() {
    if (us.getSelected() != null) {
      controller.setSelectedUser(us.getSelected());
    }
    controller.switchToScreen("HomeScreen");
  }

}
