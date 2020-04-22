package ooga.controller.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ooga.controller.UserFactory;

public class UserList implements Iterable<User>{

  private List<User> myUsers = new ArrayList<>();
  private User defaultUser;
  private User mySelectedUser;

  public UserList(){
    defaultUser = UserFactory.getDefaultUser();
    mySelectedUser = defaultUser;
  }

  public void addUser(User newUser){
    myUsers.add(newUser);
  }

  public boolean deleteUser(User existingUser){
    if (myUsers.contains(existingUser)){
     myUsers.remove(existingUser);
     return true;
    }
    //User not found, unable to delete
    return false;
  }

  public User getSelectedUser() {
    return mySelectedUser;
  }

  public void setSelectedUser(User user) {
    mySelectedUser = user;
  }

  @Override
  public Iterator<User> iterator() {
    return myUsers.iterator();
  }
}
