package ooga.controller.users;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserListTest {

  UserList myList = new UserList();

  @Test
  void iterator() {
    User user1 = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));
    User user2 = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));
    User user3 = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));
    User user4 = UserFactory.getUser(new File("data/userdata/ALL UNLOCKED.user"));

    Set<User> users = new HashSet<>();
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);
    myList.addUser(user1);
    myList.addUser(user2);
    myList.addUser(user3);
    myList.addUser(user4);

    Set<User> usersFromUserList = new HashSet<>();
    for (User user : myList){
      usersFromUserList.add(user);
    }

    assertTrue(users.containsAll(usersFromUserList));
  }
}