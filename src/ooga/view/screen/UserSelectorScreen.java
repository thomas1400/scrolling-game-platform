package ooga.view.screen;

import ooga.controller.ScreenController;
import ooga.controller.data.UserList;
import ooga.view.dynamicUI.UserSelector;

public class UserSelectorScreen extends Screen {

  private UserList myUsers;
  private UserSelector us;

  public UserSelectorScreen(ScreenController controller, UserList users) {
    super(controller);
    myUsers = users;
    us = new UserSelector(controller.getUsers());

    dynamicNodes.put("user-selector", us);

    loadLayout();
  }

  public void selectUser() {
    if (us.getSelected() != null) {
      controller.setSelectedUser(us.getSelected());
    }
    controller.switchToScreen("HomeScreen");
  }

}
