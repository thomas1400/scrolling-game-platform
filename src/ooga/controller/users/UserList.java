package ooga.controller.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ooga.exceptions.ExceptionFeedback;

public class UserList implements Iterable<User>{

  private List<User> myUsers = new ArrayList<>();
  private User mySelectedUser;

  /**
   * Creates a UserList structure which allows the simple storage and accessing of all users
   * pertinent to the application. Always contains a "DefaultUser" loaded with the UserFactory
   * class that allows you to participate in game-play even without a user profile.
   */
  public UserList(){
    mySelectedUser = UserFactory.getDefaultUser();
  }

  /**
   * @param newUser the User to be added to the UserList
   */
  public void addUser(User newUser){
    myUsers.add(newUser);
  }

  /**
   * @param existingUser the User to attempt to be deleted
   * @return true if the user's deletion is successful
   */
  public boolean deleteUser(User existingUser){
    if (myUsers.contains(existingUser)){
     myUsers.remove(existingUser);
     return true;
    }
    ExceptionFeedback.throwHandledException(new RuntimeException(),
        "User " + existingUser.getName() + " does not exist in the UserList. Cannot be deleted.");
    return false;
  }

  /**
   * @return an iterator that can iterate through the Users in the UserList
   */
  @Override
  public Iterator<User> iterator() {
    return myUsers.iterator();
  }

  /**
   * @param user the user to later be accessed and used in screens and game-play
   */
  public void setSelectedUser(User user) {
    mySelectedUser = user;
  }

  /**
   * @return the currently selected user in the UserList so that it's properties can be used for
   * game loading and game-play
   */
  public User getSelectedUser() {
    return mySelectedUser;
  }

}
