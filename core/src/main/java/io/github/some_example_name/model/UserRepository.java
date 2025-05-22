package io.github.some_example_name.model;

import java.util.HashMap;

public class UserRepository {
    private static final HashMap<String, User> users = new HashMap<>();

    public static boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) return false;
        users.put(user.getUsername(), user);
        return true;
    }

    public static boolean isUsernameTaken(String username) {
        return users.containsKey(username);
    }

    public static User findUserByUsername(String input) {
        for (String username : users.keySet()) {
            if (username.equalsIgnoreCase(input)) {
                return users.get(username);
            }
        }
        return null;
    }

    public static void deleteUser(User currentUser) {
        users.remove(currentUser.getUsername());
    }

    //UserRepository.saveUsers(); // TODO: saves users in json
}
